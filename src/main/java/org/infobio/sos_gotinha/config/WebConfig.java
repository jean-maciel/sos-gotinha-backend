package org.infobio.sos_gotinha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a configuração a todos os endpoints sob /api/
                .allowedOrigins("*") // Permite requisições de qualquer origem (bom para desenvolvimento)
                // Para produção, seria mais seguro especificar o domínio do seu front-end:
                // .allowedOrigins("https://seusite.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(false); // Se não estiver usando autenticação baseada em cookies/sessão
    }
}