package com.git.devdioge.paciente_api.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe RequestDelete, utilizada para deletar um registro do banco de dados
 * @param codigo para buscar o paciente no banco de dados.
 */

@Schema(name = "PacienteRequestDeleteDTO", description = "DTO de request de delete, deleta o paciente dos registro.",
        example = "{\"codigo\":\"6328eb6d-e960-4b61-9f8a-6aaf80911019\"}")
public record PacienteRequestDeleteDTO(

        @Schema(description = "Identificador do paciente em UUID", example = "6328eb6d-e960-4b61-9f8a-6aaf80911019")
        @NotBlank(message = "Campo codigo não pode ser nulo ou vazio.")
        String codigo){
}
