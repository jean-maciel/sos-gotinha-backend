package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroVacinaRequestDTO {
    private UUID criancaId;
    private UUID profissionalUserId; // ID do AppUser do profissional logado
    private String nomeVacina;
    private Integer dose;
    private LocalDate dataAplicacao;
    private String localAplicacao;
    private String loteVacina;
}