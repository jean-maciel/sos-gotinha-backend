package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.model.CalendarioVacina;
import org.infobio.sos_gotinha.service.CalendarioVacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/calendario-vacinas")
public class CalendarioVacinaController {

    private final CalendarioVacinaService calendarioVacinaService;

    @Autowired
    public CalendarioVacinaController(CalendarioVacinaService calendarioVacinaService) {
        this.calendarioVacinaService = calendarioVacinaService;
    }

    @GetMapping
    public List<CalendarioVacina> getCalendarioCompleto() {
        return calendarioVacinaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioVacina> getVacinaById(@PathVariable UUID id) {
        CalendarioVacina vacina = calendarioVacinaService.findById(id);
        return ResponseEntity.ok(vacina);
    }
}