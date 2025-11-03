package org.infobio.sos_gotinha.service;

import jakarta.transaction.Transactional;
import org.infobio.sos_gotinha.dto.RegistroVacinaRequestDTO;
import org.infobio.sos_gotinha.dto.RegistroVacinaResponseDTO;
import org.infobio.sos_gotinha.model.Crianca;
import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.infobio.sos_gotinha.model.RegistroVacina;
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

    @Autowired
    public RegistroVacinaService(RegistroVacinaRepository registroVacinaRepository,
                                 ProfissionalSaudeRepository profissionalSaudeRepository,
                                 CriancaRepository criancaRepository) {
        this.registroVacinaRepository = registroVacinaRepository;
        this.profissionalSaudeRepository = profissionalSaudeRepository;
        this.criancaRepository = criancaRepository;
    }

    @Transactional
    public RegistroVacinaResponseDTO salvarRegistro(RegistroVacinaRequestDTO dto) { // 1. Mude o tipo de retorno
        // ... (lógica para encontrar profissional e crianca) ...
        ProfissionalSaude profissional = profissionalSaudeRepository.findByUser_Id(dto.getProfissionalUserId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com User ID: " + dto.getProfissionalUserId()));
        Crianca crianca = criancaRepository.findById(dto.getCriancaId())
                .orElseThrow(() -> new RuntimeException("Criança não encontrada com ID: " + dto.getCriancaId()));

        RegistroVacina novoRegistro = new RegistroVacina();
        // ... (lógica para preencher o novoRegistro) ...
        novoRegistro.setCrianca(crianca);
        novoRegistro.setProfissional(profissional);
        novoRegistro.setNomeVacina(dto.getNomeVacina());
        novoRegistro.setDose(dto.getDose());
        novoRegistro.setDataAplicacao(dto.getDataAplicacao());
        novoRegistro.setLocalAplicacao(dto.getLocalAplicacao());
        novoRegistro.setLoteVacina(dto.getLoteVacina());

        RegistroVacina registroSalvo = registroVacinaRepository.save(novoRegistro); // 2. Salva a entidade

        return toDTO(registroSalvo); // 3. Retorna o DTO
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
        dto.setNomeVacina(registro.getNomeVacina());
        dto.setDose(registro.getDose());
        dto.setDataAplicacao(registro.getDataAplicacao());
        dto.setLocalAplicacao(registro.getLocalAplicacao());
        dto.setLoteVacina(registro.getLoteVacina());
        return dto;
    }

    @Transactional
    public RegistroVacinaResponseDTO editarRegistro(UUID registroId, RegistroVacinaRequestDTO dto) {
        // 1. Encontra o registro de vacina existente
        RegistroVacina registro = registroVacinaRepository.findById(registroId)
                .orElseThrow(() -> new RuntimeException("Registro de vacina não encontrado com ID: " + registroId));

        // 2. Encontra o profissional que está editando
        ProfissionalSaude profissional = profissionalSaudeRepository.findByUser_Id(dto.getProfissionalUserId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com User ID: " + dto.getProfissionalUserId()));

        // 3. Atualiza os campos (Vamos assumir que quem edita se torna o profissional do registro)
        registro.setProfissional(profissional);
        registro.setNomeVacina(dto.getNomeVacina());
        registro.setDose(dto.getDose());
        registro.setDataAplicacao(dto.getDataAplicacao());
        registro.setLocalAplicacao(dto.getLocalAplicacao());
        registro.setLoteVacina(dto.getLoteVacina());
        // Não alteramos a criança (criancaId)

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
