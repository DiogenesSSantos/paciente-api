package com.git.devdioge.paciente_api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author Diogenes Santos
 * Classe responsável para criar paciente, regras de negócio do service valida a classe, então não utilizamos constraint
 * na sua criação.
 *
 * @param nome
 * @param numero
 * @param bairro
 * @param tipoConsulta
 * @param status
 * @param dataConsulta
 */



@Schema(name = "PacienteRequestPostDTO", description = "DTO de requisição do paciente para criação de paciente.",
        example =
                "{\"nome\":\"Maria José\",\"numero\":\"81999999999\",\"bairro\":\"Alto da balança\"," +
                        "\"tipoConsulta\":\"Ortopedista\",\"status\":\"Marcado\",\"dataConsulta\":\"2026-05-22T13:00:00\"," +
                        " \"dataMarcacao\":\"2026-04-07T13:00:00\"}")
public record PacienteRequestPostDTO(
String nome,
String numero,
String bairro,
String tipoConsulta,
String status,
LocalDateTime dataConsulta){
}
