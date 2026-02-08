package com.git.devdioge.paciente_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "PacienteResponseDTO", description = "DTO de resposta do paciente",
        example =
                "{\"codigo\":\"d385b86d-a99c-456a-8ebd-ad2e903b5b50\"," +
                        "\"nome\":\"Maria José\",\"numero\":\"81999999999\",\"bairro\":\"Alto da balança\"," +
                "\"tipoConsulta\":\"Ortopedista\",\"status\":\"Marcado\",\"dataConsulta\":\"2026-05-22T13:00:00\"," +
                " \"dataMarcacao\":\"2026-04-07T13:00:00\"}")
public record PacienteResponseDTO(
        @Schema(description = "Identificador do paciente em UUID", example = "6328eb6d-e960-4b61-9f8a-6aaf80911019")
        String codigo,
        @Schema(description = "Nome do paciente", example = "Maria da Silva")
        String nome,
        @Schema(description = "Numero para contato do paciente", example = "81999999999")
        String numero,
        @Schema(description = "Bairro do paciente", example = "Das Flores")
        String bairro,
        @Schema(description = "Tipo de consulta", example = "Neurologista")
        String tipoConsulta,
        @Schema(description = "Status da consulta", example = "Marcado")
        String status,
        @Schema(description = "Data da consulta", example = "2026-05-12T13:00:00")
        LocalDateTime dataConsulta,
        @Schema(description = "Data marcação", example = "2026-04-07T13:00:00")
        LocalDateTime dataMarcacao
) {
}
