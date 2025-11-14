package org.infobio.sos_gotinha.dto;

import lombok.Data;

@Data
public class VacinaStatusDTO {
    private String nomeVacina;
    private String doseCalendario; // Ex: "1ª Dose"
    private int idadeRecomendadaMeses;
    private String status; // "EM_DIA", "EM_ATRASO", "PROXIMA"
}
