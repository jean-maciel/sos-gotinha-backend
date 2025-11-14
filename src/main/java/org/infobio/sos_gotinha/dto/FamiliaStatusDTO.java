package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class FamiliaStatusDTO {
    private UUID responsavelId;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private int totalEmDia = 0;
    private int totalEmAtraso = 0;
    private int totalProximas = 0;
    private List<CriancaStatusDTO> criancas; // Lista de crianças e seus status
}