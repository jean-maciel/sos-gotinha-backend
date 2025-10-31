package org.infobio.sos_gotinha.service;

import jakarta.transaction.Transactional;
import org.infobio.sos_gotinha.dto.RegistrationRequest;
import org.infobio.sos_gotinha.model.AppUser;
import org.infobio.sos_gotinha.model.ProfissionalSaude;
import org.infobio.sos_gotinha.model.Responsavel;
import org.infobio.sos_gotinha.model.UserRole;
import org.infobio.sos_gotinha.repository.AppUserRepository;
import org.infobio.sos_gotinha.repository.ProfissionalSaudeRepository;
import org.infobio.sos_gotinha.repository.ResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    // Dependências necessárias para o serviço
    private final AppUserRepository appUserRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Injeção de todas as dependências via construtor.
     * O Spring fornece automaticamente as instâncias necessárias.
     */
    @Autowired
    public AppUserService(
            AppUserRepository appUserRepository,
            ResponsavelRepository responsavelRepository,
            ProfissionalSaudeRepository profissionalSaudeRepository,
            PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.responsavelRepository = responsavelRepository;
        this.profissionalSaudeRepository = profissionalSaudeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra um novo usuário e seu perfil (Responsavel ou ProfissionalSaude)
     * de forma transacional, garantindo que ambas as operações sejam bem-sucedidas.
     *
     * @param request O DTO contendo todos os dados do formulário de registro.
     * @return O objeto AppUser que foi salvo no banco.
     */
    @Transactional
    public AppUser registerNewUser(RegistrationRequest request) {
        // 1. Cria a entidade principal AppUser
        AppUser newUser = new AppUser();
        newUser.setNome(request.getNome());
        newUser.setEmail(request.getEmail());
        newUser.setCpf(request.getCpf());
        newUser.setTelefone(request.getTelefone());
        newUser.setRole(request.getRole());
        // Criptografa a senha pura vinda do DTO antes de definir no objeto
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        appUserRepository.save(newUser);

        // 2. Cria a entidade de perfil associada com base na role
        if (request.getRole() == UserRole.responsavel) {
            Responsavel responsavel = new Responsavel();
            responsavel.setUser(newUser);
            responsavel.setEndereco(request.getEndereco());
            responsavelRepository.save(responsavel);
        } else if (request.getRole() == UserRole.profissional) {
            ProfissionalSaude profissional = new ProfissionalSaude();
            profissional.setUser(newUser);
            profissional.setRegistroProfissional(request.getRegistroProfissional());
            profissional.setEspecialidade(request.getEspecialidade());
            profissionalSaudeRepository.save(profissional);
        }

        return newUser;
    }

    /**
     * Autentica um usuário comparando a senha fornecida com o hash salvo no banco.
     *
     * @param email O email do usuário tentando logar.
     * @param senhaPura A senha em texto puro digitada pelo usuário.
     * @return Um Optional contendo o AppUser se a autenticação for bem-sucedida.
     */
    public Optional<AppUser> autenticar(String email, String senhaPura) {
        Optional<AppUser> userOptional = appUserRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            // passwordEncoder.matches() compara de forma segura a senha pura com o hash
            if (passwordEncoder.matches(senhaPura, user.getPasswordHash())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public Optional<AppUser> autenticarPorCpf(String cpf, String senhaPura) {
        Optional<AppUser> userOptional = appUserRepository.findByCpf(cpf);

        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            if (passwordEncoder.matches(senhaPura, user.getPasswordHash())) {
                return Optional.of(user); // CPF e senha corretos
            }
        }

        return Optional.empty(); // Usuário não encontrado ou senha incorreta
    }
    
    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    /**
     * Busca um usuário específico pelo seu ID.
     *
     * @param id O UUID do usuário.
     * @return O AppUser encontrado.
     * @throws RuntimeException se o usuário não for encontrado.
     */
    public AppUser findById(UUID id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));
    }

    public void deleteById(UUID id) {
        if (!appUserRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o id: " + id);
        }
        appUserRepository.deleteById(id);
    }
}