package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CriancaStatusDTO {
    private UUID criancaId;
    private String nomeCrianca;
    private long idadeAtualEmMeses;
    private List<VacinaStatusDTO> vacinas;
}