package br.com.fiap.efeng.config.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        .info(new Info()
            .title("Efeng API")
            .version("v1")
            .description("Documentação da API ESG Efeng"))
        .components(
            new io.swagger.v3.oas.models.Components().addSecuritySchemes(
                securitySchemeName,
                new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
        .tags(List.of(
            new Tag().name("Autenticação")
                .description("Endpoints de login e registro"),
            new Tag().name("Usuários").description("Gerenciamento de usuários"),
            new Tag().name("Dispositivos")
                .description("Cadastro e consulta de dispositivos"),
            new Tag().name("Sensores IOT")
                .description("Cadastro e consulta de sensores"),
            new Tag().name("Consumo").description("Dados de consumo energético"),
            new Tag().name("Alertas").description("Alertas e notificações"),
            new Tag().name("Limites")
                .description("Configuração de limites de consumo")

        ));
  }
}
