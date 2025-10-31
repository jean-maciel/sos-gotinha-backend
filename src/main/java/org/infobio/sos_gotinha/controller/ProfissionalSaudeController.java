package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.infobio.sos_gotinha.service.ProfissionalSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalSaudeService;

    @Autowired
    public ProfissionalSaudeController(ProfissionalSaudeService profissionalSaudeService) {
        this.profissionalSaudeService = profissionalSaudeService;
    }

    @GetMapping
    public List<ProfissionalSaude> getAllProfissionais() {
        return profissionalSaudeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaude> getProfissionalById(@PathVariable UUID id) {
        ProfissionalSaude profissional = profissionalSaudeService.findById(id);
        return ResponseEntity.ok(profissional);
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaude> createProfissional(@RequestBody ProfissionalSaude profissional) {
        ProfissionalSaude novoProfissional = profissionalSaudeService.save(profissional);
        return new ResponseEntity<>(novoProfissional, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfissional(@PathVariable UUID id) {
        profissionalSaudeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
