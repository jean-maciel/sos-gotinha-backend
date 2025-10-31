package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegistroVacinaResponseDTO {
    private UUID id;
    private String nomeVacina;
    private Integer dose;
    private LocalDate dataAplicacao;
    private String localAplicacao;
    private String loteVacina;
    // Opcional: Adicionar nome do profissional se precisar
    // private String nomeProfissional;
}