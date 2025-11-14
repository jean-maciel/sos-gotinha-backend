package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.dto.FamiliaStatusDTO;
import org.infobio.sos_gotinha.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/status-familias")
    public ResponseEntity<List<FamiliaStatusDTO>> getRelatorioStatusFamilias() {
        List<FamiliaStatusDTO> relatorio = dashboardService.getRelatorioStatusVacinal();
        return ResponseEntity.ok(relatorio);
    }
}