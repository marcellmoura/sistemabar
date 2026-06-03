package com.bar.sistemabar.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sistemaBarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SistemaBar API")
                        .version("1.0.0")
                        .description("API REST para controle de movimento diário, estoque, saídas especiais, fiado e conferência de caixa de um bar."));
    }
}