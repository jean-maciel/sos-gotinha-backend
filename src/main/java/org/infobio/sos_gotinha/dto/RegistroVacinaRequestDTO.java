package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroVacinaRequestDTO {
    private UUID criancaId;
    private UUID profissionalUserId;
    private UUID calendarioVacinaId;
    private LocalDate dataAplicacao;
    private String localAplicacao;
    private String loteVacina;
}