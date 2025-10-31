package org.infobio.sos_gotinha.service;

import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.infobio.sos_gotinha.repository.ProfissionalSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalSaudeRepository;

    @Autowired
    public ProfissionalSaudeService(ProfissionalSaudeRepository profissionalSaudeRepository) {
        this.profissionalSaudeRepository = profissionalSaudeRepository;
    }

    public List<ProfissionalSaude> findAll() {
        return profissionalSaudeRepository.findAll();
    }

    public ProfissionalSaude findById(UUID id) {
        return profissionalSaudeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado com o id: " + id));
    }

    public ProfissionalSaude save(ProfissionalSaude profissionalSaude) {
        return profissionalSaudeRepository.save(profissionalSaude);
    }

    public void deleteById(UUID id) {
        if (!profissionalSaudeRepository.existsById(id)) {
            throw new RuntimeException("Profissional de saúde não encontrado com o id: " + id);
        }
        profissionalSaudeRepository.deleteById(id);
    }
}