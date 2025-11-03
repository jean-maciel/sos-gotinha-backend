package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.dto.RegistroVacinaRequestDTO;
import org.infobio.sos_gotinha.dto.RegistroVacinaResponseDTO;
import org.infobio.sos_gotinha.model.RegistroVacina;
import org.infobio.sos_gotinha.service.RegistroVacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registros-vacinas")
public class RegistroVacinaController {

    private final RegistroVacinaService registroVacinaService;

    @Autowired
    public RegistroVacinaController(RegistroVacinaService registroVacinaService) {
        this.registroVacinaService = registroVacinaService;
    }

    @GetMapping
    public List<RegistroVacina> getAllRegistros() {
        return registroVacinaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroVacina> getRegistroById(@PathVariable UUID id) {
        RegistroVacina registro = registroVacinaService.findById(id);
        return ResponseEntity.ok(registro);
    }

    @PostMapping
    public ResponseEntity<RegistroVacinaResponseDTO> createRegistro(@RequestBody RegistroVacinaRequestDTO registroDTO) { // Mude o tipo de retorno
        RegistroVacinaResponseDTO novoRegistroDTO = registroVacinaService.salvarRegistro(registroDTO);
        return new ResponseEntity<>(novoRegistroDTO, HttpStatus.CREATED);
    }

    @GetMapping("/por-crianca/{criancaId}")
    public ResponseEntity<List<RegistroVacinaResponseDTO>> getRegistrosPorCrianca(@PathVariable UUID criancaId) {
        List<RegistroVacinaResponseDTO> registros = registroVacinaService.findByCriancaId(criancaId);
        return ResponseEntity.ok(registros);
    }

    @PutMapping("/{registroId}")
    public ResponseEntity<RegistroVacinaResponseDTO> updateRegistro(
            @PathVariable UUID registroId,
            @RequestBody RegistroVacinaRequestDTO registroDTO) {
        RegistroVacinaResponseDTO registroAtualizado = registroVacinaService.editarRegistro(registroId, registroDTO);
        return ResponseEntity.ok(registroAtualizado);
    }

    @DeleteMapping("/{registroId}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable UUID registroId) {
        registroVacinaService.deleteRegistro(registroId);
        return ResponseEntity.noContent().build(); // HTTP 204
    }
}