package com.unisal.api_venta_boletos_cine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Venta de Boletos de Cine")
                        .version("1.0")
                        .description("Documentación interactiva de los endpoints del sistema de cine. Proyecto POO Ciclo II/2026."));
    }
}