package org.infobio.sos_gotinha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Este Bean define o BCrypt como o codificador de senhas padrão da aplicação.
     * O Spring irá gerenciá-lo e poderemos injetá-lo em outras classes, como na AppUserService.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * IMPORTANTE: Por padrão, o Spring Security bloqueia todos os endpoints.
     * Este Bean desabilita essa proteção para que você possa continuar testando sua API
     * sem precisar de autenticação por enquanto.
     * Futuramente, você irá configurar aqui quais endpoints são públicos e quais são protegidos.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF (necessário para APIs stateless)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite todas as requisições por enquanto
                );
        return http.build();
    }
}