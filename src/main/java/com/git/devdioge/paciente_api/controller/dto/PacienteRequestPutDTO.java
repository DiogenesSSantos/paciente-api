package com.git.devdioge.paciente_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Classe responsável para atualização completa do paciente, todos os campos são obrigatórios qualquer quebra das
 * constraint será lançada a exception MethodArgumentNotValidException, caso queria atualizar campo específico mantendo
 * o estado dos campos não alterados utilize o method Patch e a classe PacienteRequestPatchDTO.
 *
 * @param codigo
 * @param nome
 * @param numero
 * @param bairro
 * @param tipoConsulta
 * @param status
 * @param dataConsulta
 * @param dataMarcacao
 */

@Schema(name = "PacienteRequestPutDTO", description = "DTO de requisição do paciente, atualizar por completo.",
        example =
                "{\"codigo\":\"d385b86d-a99c-456a-8ebd-ad2e903b5b50\"," +
                        "\"nome\":\"Maria José\",\"numero\":\"81999999999\",\"bairro\":\"nome_bairro\"," +
                        "\"tipoConsulta\":\"Ortopedista\",\"status\":\"Marcado\",\"dataConsulta\":\"2026-05-22T13:00:00\"," +
                        " \"dataMarcacao\":\"2026-04-07T13:00:00\"}")
public record PacienteRequestPutDTO(

        @Schema(description = "Identificador do paciente em UUID", example = "6328eb6d-e960-4b61-9f8a-6aaf80911019")
        @NotBlank
        String codigo,

        @Schema(description = "Nome do paciente", example = "Maria da Silva")
        @NotBlank
        String nome,

        @Schema(description = "Numero para contato do paciente", example = "81999999999")
        @NotBlank
        String numero,

        @Schema(description = "Bairro do paciente", example = "Das Flores")
        @NotBlank
        String bairro,

        @Schema(description = "Tipo de consulta", example = "Neurologista")
        @NotBlank
        String tipoConsulta,

        @Schema(description = "Status da consulta", example = "Marcado")
        @NotBlank
        String status,

        @Schema(description = "Data da consulta", example = "2026-05-12T13:00:00")
        LocalDateTime dataConsulta,

        @Schema(description = "Data marcação", example = "2026-04-07T13:00:00")
        LocalDateTime dataMarcacao
) {
}
