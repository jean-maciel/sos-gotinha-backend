package org.infobio.sos_gotinha.controller;

import org.infobio.sos_gotinha.dto.RegistrationRequest;
import org.infobio.sos_gotinha.model.AppUser;
import org.infobio.sos_gotinha.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    // 2. Altere o parâmetro para receber o RegistrationRequest
    public ResponseEntity<AppUser> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        // 3. Chame o novo método 'registerNewUser' do serviço
        AppUser savedUser = appUserService.registerNewUser(registrationRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }



    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String cpf = credentials.get("cpf"); // Altere de "email" para "cpf"
        String password = credentials.get("password");

        // Chame o novo método de autenticação por CPF
        return appUserService.autenticarPorCpf(cpf, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable UUID id) {
        AppUser user = appUserService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        appUserService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}