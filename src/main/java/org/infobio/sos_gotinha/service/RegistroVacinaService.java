package org.infobio.sos_gotinha.service;

import org.infobio.sos_gotinha.dto.RegistroVacinaResponseDTO;
import org.infobio.sos_gotinha.model.RegistroVacina;
import org.infobio.sos_gotinha.repository.RegistroVacinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroVacinaService {

    private final RegistroVacinaRepository registroVacinaRepository;

    @Autowired
    public RegistroVacinaService(RegistroVacinaRepository registroVacinaRepository) {
        this.registroVacinaRepository = registroVacinaRepository;
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

    public List<RegistroVacinaResponseDTO> findByCriancaId(UUID criancaId) {
        List<RegistroVacina> registros = registroVacinaRepository.findByCrianca_IdOrderByDataAplicacaoDesc(criancaId);

        // Converte para DTO
        return registros.stream().map(registro -> {
            RegistroVacinaResponseDTO dto = new RegistroVacinaResponseDTO();
            dto.setId(registro.getId());
            dto.setNomeVacina(registro.getNomeVacina());
            dto.setDose(registro.getDose());
            dto.setDataAplicacao(registro.getDataAplicacao());
            dto.setLocalAplicacao(registro.getLocalAplicacao());
            dto.setLoteVacina(registro.getLoteVacina());
            // Se precisar do nome do profissional, teria que buscar aqui
            return dto;
        }).collect(Collectors.toList());
    }
}
