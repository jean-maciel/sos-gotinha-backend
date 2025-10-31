package org.infobio.sos_gotinha.service;

import jakarta.transaction.Transactional;
import org.infobio.sos_gotinha.dto.CriancaRequestDTO;
import org.infobio.sos_gotinha.dto.CriancaResponseDTO;
import org.infobio.sos_gotinha.model.Crianca;
import org.infobio.sos_gotinha.model.Responsavel;
import org.infobio.sos_gotinha.repository.CriancaRepository;
import org.infobio.sos_gotinha.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CriancaService {

    private final CriancaRepository criancaRepository;
    private final ResponsavelRepository responsavelRepository;

    @Autowired
    public CriancaService(CriancaRepository criancaRepository, ResponsavelRepository responsavelRepository) {
        this.criancaRepository = criancaRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @Transactional
    public Crianca cadastrarNovaCrianca(CriancaRequestDTO dto) {
        // 1. Encontra o perfil do Responsável (código existente)
        Responsavel responsavel = responsavelRepository.findByUser_Id(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado para o User ID: " + dto.getUserId()));

        // 2. Cria a nova entidade Crianca
        Crianca novaCrianca = new Crianca();
        novaCrianca.setNomeCompleto(dto.getNomeCompleto());
        novaCrianca.setNumeroSus(dto.getNumeroSus());
        novaCrianca.setCpf(dto.getCpf()); // <-- ADICIONE ESTA LINHA

        // Converte a data de String para LocalDate (código existente)
        novaCrianca.setDataNascimento(LocalDate.parse(dto.getDataNascimento(), DateTimeFormatter.ISO_LOCAL_DATE));

        // 3. Associa o responsável à criança (código existente)
        novaCrianca.setResponsaveis(Collections.singleton(responsavel));

        return criancaRepository.save(novaCrianca);
    }

    public List<CriancaResponseDTO> findByUserId(UUID userId) {
        List<Crianca> criancas = criancaRepository.findByResponsaveis_User_Id(userId);

        // Converte a lista de Entidades para uma lista de DTOs
        return criancas.stream().map(crianca -> {
            CriancaResponseDTO dto = new CriancaResponseDTO();
            dto.setId(crianca.getId());
            dto.setNomeCompleto(crianca.getNomeCompleto());
            dto.setDataNascimento(crianca.getDataNascimento());
            dto.setNumeroSus(crianca.getNumeroSus());
            dto.setCpf(crianca.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<Crianca> findAll() {
        return criancaRepository.findAll();
    }

    public Crianca findById(UUID id) {
        return criancaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Criança não encontrada com o id: " + id));
    }

    public Crianca save(Crianca crianca) {
        // Validações de negócio (ex: verificar se o CPF já existe) podem ser adicionadas aqui
        return criancaRepository.save(crianca);
    }

    public void deleteById(UUID id) {
        if (!criancaRepository.existsById(id)) {
            throw new RuntimeException("Criança não encontrada com o id: " + id);
        }
        criancaRepository.deleteById(id);
    }
}