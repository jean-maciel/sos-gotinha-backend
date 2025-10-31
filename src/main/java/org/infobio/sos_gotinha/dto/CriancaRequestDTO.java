package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CriancaRequestDTO {
    private String nomeCompleto;
    private String dataNascimento; // Receberemos como String (ex: "2023-10-27")
    private String numeroSus;
    private String cpf;
    private UUID userId; // ID do AppUser do responsável que está cadastrando
}