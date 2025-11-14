package org.infobio.sos_gotinha.service;

import jakarta.transaction.Transactional;
import org.infobio.sos_gotinha.dto.RegistroVacinaRequestDTO;
import org.infobio.sos_gotinha.dto.RegistroVacinaResponseDTO;
import org.infobio.sos_gotinha.model.CalendarioVacina;
import org.infobio.sos_gotinha.model.Crianca;
import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.infobio.sos_gotinha.model.RegistroVacina;
import org.infobio.sos_gotinha.repository.CalendarioVacinaRepository;
import org.infobio.sos_gotinha.repository.CriancaRepository;
import org.infobio.sos_gotinha.repository.ProfissionalSaudeRepository;
import org.infobio.sos_gotinha.repository.RegistroVacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroVacinaService {

    private final RegistroVacinaRepository registroVacinaRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final CriancaRepository criancaRepository;
    private final CalendarioVacinaRepository calendarioVacinaRepository;

    @Autowired
    public RegistroVacinaService(RegistroVacinaRepository registroVacinaRepository,
                                 ProfissionalSaudeRepository profissionalSaudeRepository,
                                 CriancaRepository criancaRepository,
                                 CalendarioVacinaRepository calendarioVacinaRepository) { // 2. Adicionar ao construtor
        this.registroVacinaRepository = registroVacinaRepository;
        this.profissionalSaudeRepository = profissionalSaudeRepository;
        this.criancaRepository = criancaRepository;
        this.calendarioVacinaRepository = calendarioVacinaRepository;
    }

    @Transactional
    public RegistroVacinaResponseDTO salvarRegistro(RegistroVacinaRequestDTO dto) {
        ProfissionalSaude profissional = profissionalSaudeRepository.findByUser_Id(dto.getProfissionalUserId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado..."));
        Crianca crianca = criancaRepository.findById(dto.getCriancaId())
                .orElseThrow(() -> new RuntimeException("Criança não encontrada..."));

        // 3. Busca o item do calendário que foi selecionado
        CalendarioVacina itemCalendario = calendarioVacinaRepository.findById(dto.getCalendarioVacinaId())
                .orElseThrow(() -> new RuntimeException("Item do calendário não encontrado com ID: " + dto.getCalendarioVacinaId()));

        RegistroVacina novoRegistro = new RegistroVacina();
        novoRegistro.setCrianca(crianca);
        novoRegistro.setProfissional(profissional);
        novoRegistro.setCalendarioVacina(itemCalendario); // 4. Associa o item
        novoRegistro.setDataAplicacao(dto.getDataAplicacao());
        novoRegistro.setLocalAplicacao(dto.getLocalAplicacao());
        novoRegistro.setLoteVacina(dto.getLoteVacina());

        RegistroVacina registroSalvo = registroVacinaRepository.save(novoRegistro);
        return toDTO(registroSalvo);
    }

    // --- MÉTODO DE LISTAGEM ATUALIZADO ---
    public List<RegistroVacinaResponseDTO> findByCriancaId(UUID criancaId) {
        List<RegistroVacina> registros = registroVacinaRepository.findByCrianca_IdOrderByDataAplicacaoDesc(criancaId);
        // 4. Usa o método auxiliar
        return registros.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // --- MÉTODO AUXILIAR REUTILIZÁVEL ---
    private RegistroVacinaResponseDTO toDTO(RegistroVacina registro) {
        RegistroVacinaResponseDTO dto = new RegistroVacinaResponseDTO();
        dto.setId(registro.getId());
        dto.setDataAplicacao(registro.getDataAplicacao());
        dto.setLocalAplicacao(registro.getLocalAplicacao());
        dto.setLoteVacina(registro.getLoteVacina());

        // 5. Pega os dados da entidade associada
        if (registro.getCalendarioVacina() != null) {
            dto.setNomeVacina(registro.getCalendarioVacina().getNomeVacina());
            dto.setDose(registro.getCalendarioVacina().getDose());
        }
        return dto;
    }

    @Transactional
    public RegistroVacinaResponseDTO editarRegistro(UUID registroId, RegistroVacinaRequestDTO dto) {
        RegistroVacina registro = registroVacinaRepository.findById(registroId)
                .orElseThrow(() -> new RuntimeException("Registro de vacina não encontrado..."));
        ProfissionalSaude profissional = profissionalSaudeRepository.findByUser_Id(dto.getProfissionalUserId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado..."));

        // 3. Busca o NOVO item do calendário
        CalendarioVacina itemCalendario = calendarioVacinaRepository.findById(dto.getCalendarioVacinaId())
                .orElseThrow(() -> new RuntimeException("Item do calendário não encontrado com ID: " + dto.getCalendarioVacinaId()));

        registro.setProfissional(profissional);
        registro.setCalendarioVacina(itemCalendario); // 4. Associa o novo item
        registro.setDataAplicacao(dto.getDataAplicacao());
        registro.setLocalAplicacao(dto.getLocalAplicacao());
        registro.setLoteVacina(dto.getLoteVacina());

        RegistroVacina registroSalvo = registroVacinaRepository.save(registro);
        return toDTO(registroSalvo);
    }

    // --- NOVO MÉTODO DE DELETAR ---
    public void deleteRegistro(UUID registroId) {
        if (!registroVacinaRepository.existsById(registroId)) {
            throw new RuntimeException("Registro de vacina não encontrado com ID: " + registroId);
        }
        registroVacinaRepository.deleteById(registroId);
    }

    public List<RegistroVacina> findAll() {
        return registroVacinaRepository.findAll();
    }

    public RegistroVacina findById(UUID id) {
        return registroVacinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de vacina não encontrado com o id: " + id));
    }

    public RegistroVacina save(RegistroVacina registroVacina) {
        // Lógica de negócio, como verificar se a criança e o profissional existem antes de salvar.
        return registroVacinaRepository.save(registroVacina);
    }

    public void deleteById(UUID id) {
        if (!registroVacinaRepository.existsById(id)) {
            throw new RuntimeException("Registro de vacina não encontrado com o id: " + id);
        }
        registroVacinaRepository.deleteById(id);
    }

}
