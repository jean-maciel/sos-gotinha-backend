package org.infobio.sos_gotinha.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ResponsavelResponseDTO {
    private UUID responsavelId; // ID do perfil Responsavel
    private UUID userId;        // ID do AppUser associado
    private String nome;        // Nome do AppUser
    private String email;       // Email do AppUser
    private String telefone;    // Telefone do AppUser
    private String endereco;    // Endereço do Responsavel
}