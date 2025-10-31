package org.infobio.sos_gotinha.dto;

import lombok.Data;
import org.infobio.sos_gotinha.model.UserRole;

// DTO para encapsular todos os dados do formulário de registro
@Data
public class RegistrationRequest {
    // Campos do AppUser
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private String password; // Recebemos a senha pura aqui
    private UserRole role;

    // Campos do Responsavel
    private String endereco;

    // Campos do ProfissionalSaude
    private String registroProfissional;
    private String especialidade;
}