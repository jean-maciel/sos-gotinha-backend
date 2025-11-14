package org.infobio.sos_gotinha.service;

import org.infobio.sos_gotinha.dto.CriancaStatusDTO;
import org.infobio.sos_gotinha.dto.FamiliaStatusDTO;
import org.infobio.sos_gotinha.dto.VacinaStatusDTO;
import org.infobio.sos_gotinha.model.CalendarioVacina;
import org.infobio.sos_gotinha.model.Crianca;
import org.infobio.sos_gotinha.model.Responsavel;
import org.infobio.sos_gotinha.repository.CalendarioVacinaRepository;
import org.infobio.sos_gotinha.repository.CriancaRepository;
import org.infobio.sos_gotinha.repository.RegistroVacinaRepository;
import org.infobio.sos_gotinha.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ResponsavelRepository responsavelRepository;
    @Autowired
    private CriancaRepository criancaRepository;
    @Autowired
    private RegistroVacinaRepository registroVacinaRepository;
    @Autowired
    private CalendarioVacinaRepository calendarioVacinaRepository;

    /**
     * Gera um relatório completo do status vacinal para todas as famílias cadastradas.
     * Esta lógica foi refatorada para usar a relação direta entre RegistroVacina
     * e CalendarioVacina, garantindo precisão na verificação de doses.
     *
     * @return Lista de DTOs, cada um representando o status de uma família.
     */
    public List<FamiliaStatusDTO> getRelatorioStatusVacinal() {
        // 1. Busca todos os dados necessários do banco
        List<Responsavel> todasFamilias = responsavelRepository.findAll();
        List<CalendarioVacina> calendarioCompleto = calendarioVacinaRepository.findAll();
        List<FamiliaStatusDTO> relatorio = new ArrayList<>();

        // 2. Itera sobre cada família (Responsável)
        for (Responsavel responsavel : todasFamilias) {
            FamiliaStatusDTO familiaDTO = new FamiliaStatusDTO();
            familiaDTO.setResponsavelId(responsavel.getId());
            if (responsavel.getUser() != null) {
                familiaDTO.setNomeResponsavel(responsavel.getUser().getNome());
                familiaDTO.setTelefoneResponsavel(responsavel.getUser().getTelefone());
            }

            // 3. Busca as crianças daquela família
            assert responsavel.getUser() != null;
            List<Crianca> criancas = criancaRepository.findByResponsaveis_User_Id(responsavel.getUser().getId());
            List<CriancaStatusDTO> criancasDTOList = new ArrayList<>();

            // 4. Itera sobre cada criança da família
            for (Crianca crianca : criancas) {
                CriancaStatusDTO criancaDTO = new CriancaStatusDTO();
                criancaDTO.setCriancaId(crianca.getId());
                criancaDTO.setNomeCrianca(crianca.getNomeCompleto());

                // Calcula a idade atual da criança em meses
                long idadeEmMeses = ChronoUnit.MONTHS.between(crianca.getDataNascimento(), LocalDate.now());
                criancaDTO.setIdadeAtualEmMeses(idadeEmMeses);

                // --- LÓGICA DE COMPARAÇÃO REATORADA ---
                // Busca os IDs de todas as vacinas do calendário que a criança já tomou.
                Set<UUID> idsVacinasTomadas = registroVacinaRepository.findByCrianca_IdOrderByDataAplicacaoDesc(crianca.getId())
                        .stream()
                        .map(registro -> registro.getCalendarioVacina().getId())
                        .collect(Collectors.toSet());

                List<VacinaStatusDTO> vacinasStatusList = new ArrayList<>();

                // 5. Compara o histórico da criança com o calendário completo
                for (CalendarioVacina vacinaCalendario : calendarioCompleto) {
                    VacinaStatusDTO statusDTO = new VacinaStatusDTO();
                    statusDTO.setNomeVacina(vacinaCalendario.getNomeVacina());
                    statusDTO.setDoseCalendario(vacinaCalendario.getDose());
                    int idadeRecomendada = vacinaCalendario.getIdadeRecomendadaMeses();
                    statusDTO.setIdadeRecomendadaMeses(idadeRecomendada);

                    // A verificação agora é 100% precisa, baseada no ID.
                    boolean vacinaTomada = idsVacinasTomadas.contains(vacinaCalendario.getId());

                    if (vacinaTomada) {
                        statusDTO.setStatus("EM_DIA");
                        familiaDTO.setTotalEmDia(familiaDTO.getTotalEmDia() + 1);
                    } else {
                        // Se não tomou, verifica a idade
                        if (idadeEmMeses > idadeRecomendada) {
                            statusDTO.setStatus("EM_ATRASO");
                            familiaDTO.setTotalEmAtraso(familiaDTO.getTotalEmAtraso() + 1);
                        } else if (idadeEmMeses >= idadeRecomendada - 1) { // 1 mês de antecedência
                            statusDTO.setStatus("PROXIMA");
                            familiaDTO.setTotalProximas(familiaDTO.getTotalProximas() + 1);
                        } else {
                            // "PENDENTE" - Ainda não está na hora
                        }
                    }
                    // Só adiciona na lista se tiver um status relevante (dia, atraso, próxima)
                    if (statusDTO.getStatus() != null) {
                        vacinasStatusList.add(statusDTO);
                    }
                }
                criancaDTO.setVacinas(vacinasStatusList);
                criancasDTOList.add(criancaDTO);
            }
            familiaDTO.setCriancas(criancasDTOList);
            relatorio.add(familiaDTO);
        }
        return relatorio;
    }
}