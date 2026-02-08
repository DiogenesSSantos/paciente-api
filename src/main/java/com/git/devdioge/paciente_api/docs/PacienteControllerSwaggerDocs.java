package com.git.devdioge.paciente_api.docs;


import com.git.devdioge.paciente_api.controller.dto.*;
import com.git.devdioge.paciente_api.docs.classerepresentacioanal.PacienteRepresentacional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author DiogenesSantos
 * Classe para documentar os end-points para SwagggerOpenAPI.
 */


@Tag(name = "API-PACIENTE", description = "Api para consumir recursos de pacientes.")
public interface PacienteControllerSwaggerDocs {


    @Operation(summary = "Buscar todos pacientes no banco de dados.",
            description = "Retorna uma lista de JSON com todos pacientes registrado no banco de dados ou uma lista vazia.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = PacienteResponseDTO.class)
                            )
                    ),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<List<PacienteResponseDTO>> buscarTodos();


    @Operation(summary = "Salva o paciente no banco de dados.",
            description = "Salva o paciente no banco de dados e retorna o Json desse paciente salvo.",
            responses = {
                    @ApiResponse(description = "Create", responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)
                            )),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.BAD_REQUEST_400)
                            ))
                    ,
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)

            })
    ResponseEntity<PacienteResponseDTO> salvar(PacienteRequestPostDTO pacienteRequestPostDTO);


    @Operation(summary = "Busca o paciente por Id",
            description = "Ao buscar o paciente por ID retorna JSON do paciente.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)
                            )),
                    @ApiResponse(description = "Not found", responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.NOT_FOUND_404)
                            )),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }

    )
    ResponseEntity<PacienteResponseDTO> buscarPorId(Long id);


    @Operation(summary = "Busca o paciente por Id",
            description = "Ao buscar o paciente por ID retorna JSON do paciente.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)
                            )),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.NOT_FOUND_CODIGO_404)
                            )),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }

    )
    ResponseEntity<PacienteResponseDTO> buscarPorCodigo(String codigo);


    @Operation(summary = "Deletar paciente do banco de dados por codigo.",
            description = "Ao deletar paciente por codigo o seu retorno e Void, no_content mesmo para paciente não existente.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "204",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)}
    )
    ResponseEntity<Void> deletar(@RequestBody PacienteRequestDeleteDTO requestDTO);


    @Operation(summary = "Atualização parcial do paciente",
            description = "Atualização parcial, atualiza 1 ou mais campos retorna JSON do paciente atualizado.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)
                            )),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.BAD_REQUEST_400)
                            )),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.NOT_FOUND_CODIGO_404)
                            )),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }

    )
    ResponseEntity<PacienteResponseDTO> atualizarParcial(PacienteRequestPatchDTO pacienteRequestPatchDTO);




    @Operation(summary = "Atualização por completo do paciente",
            description = "Atualização todos os campos e retorna JSON do paciente atualizado.",
            responses = {
                    @ApiResponse(description = "Sucesso", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PacienteResponseDTO.class)
                            )),
                    @ApiResponse(description = "Bad Request", responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.BAD_REQUEST_400)
                            )),
                    @ApiResponse(description = "Not found", responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(example = PacienteRepresentacional.NOT_FOUND_CODIGO_404)
                            )),
                    @ApiResponse(description = "Internal error", responseCode = "500", content = @Content)
            }

    )
    ResponseEntity<PacienteResponseDTO> atualizar(PacienteRequestPutDTO pacienteRequestPutDTO);
}
