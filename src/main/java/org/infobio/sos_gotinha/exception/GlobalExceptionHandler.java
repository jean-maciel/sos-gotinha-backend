package org.infobio.sos_gotinha.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice // Indica que esta classe trata exceções globalmente
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class) // Captura erros de integridade do BD
    public ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        String errorMessage = "Erro de integridade no banco de dados."; // Mensagem padrão
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Status padrão

        // Verifica se a causa raiz contém a mensagem específica da constraint do SUS
        if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("criancas_numero_sus_key")) {
            errorMessage = "Este número do SUS já está cadastrado.";
            status = HttpStatus.CONFLICT; // 409 Conflict é mais apropriado para duplicidade
        } else if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("criancas_cpf_key")) { // Podemos adicionar para CPF também
            errorMessage = "Este CPF já está cadastrado para outra criança.";
            status = HttpStatus.CONFLICT;
        }
        // Adicione outras verificações para constraints diferentes aqui, se necessário

        Map<String, String> body = Map.of("error", errorMessage);
        return new ResponseEntity<>(body, status);
    }

    // Você pode adicionar outros @ExceptionHandler para diferentes tipos de exceção aqui
}