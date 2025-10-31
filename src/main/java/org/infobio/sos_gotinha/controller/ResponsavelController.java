package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.dto.ResponsavelResponseDTO;
import org.infobio.sos_gotinha.model.Responsavel;
import org.infobio.sos_gotinha.service.ResponsavelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/responsaveis")
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    @Autowired
    public ResponsavelController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsavelResponseDTO>> getAllResponsaveis() {
        List<ResponsavelResponseDTO> responsaveis = responsavelService.findAllResponsaveisDTO();
        return ResponseEntity.ok(responsaveis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responsavel> getResponsavelById(@PathVariable UUID id) {
        Responsavel responsavel = responsavelService.findById(id);
        return ResponseEntity.ok(responsavel);
    }

    @PostMapping
    public ResponseEntity<Responsavel> createResponsavel(@RequestBody Responsavel responsavel) {
        Responsavel novoResponsavel = responsavelService.save(responsavel);
        return new ResponseEntity<>(novoResponsavel, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponsavel(@PathVariable UUID id) {
        responsavelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}