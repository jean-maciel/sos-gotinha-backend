package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroVacinaResponseDTO {
    private UUID id;
    private String nomeVacina; // <-- Virá da associação
    private String dose;       // <-- Virá da associação (Mudou de Integer para String)
    private LocalDate dataAplicacao;
    private String localAplicacao;
    private String loteVacina;
}