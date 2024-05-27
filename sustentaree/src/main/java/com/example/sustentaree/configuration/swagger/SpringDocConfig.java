package com.example.sustentaree.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfig {
@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Sustentare API")
                        .version("1.0.0")
                        .description("Documentação da API do projeto Sustentare")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                )
                .servers(Arrays.asList(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080")
                                .description("Local server")
                ));
    }
}
