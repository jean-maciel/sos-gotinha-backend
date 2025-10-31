package org.infobio.sos_gotinha.service;

import org.infobio.sos_gotinha.dto.ResponsavelResponseDTO;
import org.infobio.sos_gotinha.model.Responsavel;
import org.infobio.sos_gotinha.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;

    @Autowired
    public ResponsavelService(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    public List<Responsavel> findAll() {
        return responsavelRepository.findAll();
    }

    public Responsavel findById(UUID id) {
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado com o id: " + id));
    }

    public Responsavel save(Responsavel responsavel) {
        // Validações de negócio podem ser adicionadas aqui
        return responsavelRepository.save(responsavel);
    }

    public void deleteById(UUID id) {
        if (!responsavelRepository.existsById(id)) {
            throw new RuntimeException("Responsável não encontrado com o id: " + id);
        }
        responsavelRepository.deleteById(id);
    }

    public List<ResponsavelResponseDTO> findAllResponsaveisDTO() {
        List<Responsavel> responsaveis = responsavelRepository.findAll();

        // Mapeia cada entidade Responsavel para o DTO
        return responsaveis.stream().map(resp -> {
            ResponsavelResponseDTO dto = new ResponsavelResponseDTO();
            dto.setResponsavelId(resp.getId());
            dto.setEndereco(resp.getEndereco());
            if (resp.getUser() != null) {
                dto.setUserId(resp.getUser().getId());
                dto.setNome(resp.getUser().getNome());
                dto.setEmail(resp.getUser().getEmail());
                dto.setTelefone(resp.getUser().getTelefone());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}