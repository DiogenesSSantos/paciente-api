package com.git.devdioge.paciente_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Diogenes santos
 * Classe responsável em configuração a documentação OpenAPI.
 */

@Configuration
public class OpenAPIConfig {


    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                        new Info().title("Api paciente")
                                .description("Api-Rest para consumo e registro de dados, " +
                                        "utilizado para um serviço de automatização de mensagem para marcações e " +
                                        "consulta dos pacientes da rede publica de saúde de Vitória de Santo Antão.")
                                .version("1.0.0")
                                .contact(new Contact().url("https://diogenesssantos.github.io/meu-portfolio/")
                                        .name("Diogenes S Santos").email("diogenescontatoofficial@hotmail.com"))
                                .summary("Facilita a comunicação entre serviços de agendamento e pacientes via WhatsApp")
                                .license(new License()
                                        .name("MIT License")
                                        .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/DiogenesSSantos"))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação JavaDoc")
                        .url("http://devdiogenes.shop/apidocs/index.html"));
    }


    @Bean
    public GroupedOpenApi whatsappControle() {
        return GroupedOpenApi.builder()
                .group("PacienteController")
                .pathsToMatch("/pacientes/**")
                .build();

    }


}
