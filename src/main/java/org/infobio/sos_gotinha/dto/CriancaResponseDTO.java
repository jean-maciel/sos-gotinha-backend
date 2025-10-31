package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CriancaResponseDTO {
    private UUID id;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String numeroSus;
    private String cpf;
}