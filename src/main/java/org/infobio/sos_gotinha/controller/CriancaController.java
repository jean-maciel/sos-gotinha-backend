package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.dto.CriancaRequestDTO;
import org.infobio.sos_gotinha.dto.CriancaResponseDTO;
import org.infobio.sos_gotinha.model.Crianca;
import org.infobio.sos_gotinha.service.CriancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/criancas")
public class CriancaController {

    private final CriancaService criancaService;

    @Autowired
    public CriancaController(CriancaService criancaService) {
        this.criancaService = criancaService;
    }

    @GetMapping
    public List<Crianca> getAllCriancas() {
        return criancaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crianca> getCriancaById(@PathVariable UUID id) {
        Crianca crianca = criancaService.findById(id);
        return ResponseEntity.ok(crianca);
    }

    @PostMapping
    public ResponseEntity<Crianca> createCrianca(@RequestBody CriancaRequestDTO criancaDTO) {
        Crianca novaCrianca = criancaService.cadastrarNovaCrianca(criancaDTO);
        return new ResponseEntity<>(novaCrianca, HttpStatus.CREATED);
    }

    @GetMapping("/por-responsavel/{userId}")
    public ResponseEntity<List<CriancaResponseDTO>> getCriancasPorResponsavel(@PathVariable UUID userId) {
        List<CriancaResponseDTO> criancasDTO = criancaService.findByUserId(userId);
        return ResponseEntity.ok(criancasDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrianca(@PathVariable UUID id) {
        criancaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}