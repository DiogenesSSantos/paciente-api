package com.git.devdioge.paciente_api;


import com.git.devdioge.paciente_api.config.TestConfigs;
import com.git.devdioge.paciente_api.controller.dto.PacienteResponseDTO;
import com.git.devdioge.paciente_api.exceptionhandler.Problema;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteIntegrationTest {

    private ObjectMapper mapper;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
        mapper = new ObjectMapper();
    }


    @Test
    @Order(1)
    void testQuandoBuscarTodos_DeveTerStatus200_EOCorpoDaRespostaSeraListaPacienteOuListaVazia() {
        int statusOk = 200;

        Response response = given()
                .when()
                .get("/pacientes")
                .thenReturn();

        List<PacienteResponseDTO> pacienteResponseDTOS = mapper.readValue(response.getBody().asString(),
                new TypeReference<>() {
                });


        assertNotNull(response.getBody());
        assertEquals(statusOk, response.getStatusCode());
    }


    @Test
    @Order(2)
    void testQuandoBuscarPorId_DeveTerStatus200_EOCorpoDaRespostaSeraOPaciente() {
        int expectativaStatusOk = 200;
        int expectativaId = 1;

        Response response = given()
                .when()
                .get("/pacientes/{id}", expectativaId)
                .thenReturn();

        assertEquals(expectativaStatusOk, response.getStatusCode());
        assertNotNull(response.getBody().as(PacienteResponseDTO.class).codigo());
    }


    @Test
    @Order(2)
    void testQuandoBuscarPorIdQueNaoExisteOuNull_DeveTerStatus404_EaMSGExceptionPacienteIDxNaolocalidoNoBancoDeDados() {
        int expectativaStatusNotFound = 404;
        long expectativaIdNaoExiste = 0;
        String expectativaMsgException =
                String.format("Paciente de id [%d] não localizado no banco de dados ", expectativaIdNaoExiste);


        Response response = given()
                .when()
                .get("/pacientes/{id}", expectativaIdNaoExiste)
                .thenReturn();

        assertEquals(expectativaMsgException, response.as(Problema.class).mensagem());
        assertEquals(expectativaStatusNotFound, response.getStatusCode());

    }


    @Test
    @Order(3)
    void testQuandoBuscarPorCodigoExistente_DeveRetornarOPaciente_EStatusCode200() {
        String codigoValido = "7ac6865b-397d-4038-8fa8-36855b5de619";
        PacienteResponseDTO PacienteResponseDTO;
        int statusCode200 = 200;

        Response response = given()
                .when()
                .get("/pacientes/codigo/{codigo}", codigoValido)
                .thenReturn();

        assertEquals(statusCode200, response.getStatusCode());
        log.info(response.asString());
    }


    @Test
    @Order(4)
    void testQuandoBuscarOPacienteComCodigoNaoExistente_DeveRetornarUmProblemaComStatusCode404NotFound() {
        final String codigoInvalido = "AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA";
        final String msgError = "Paciente de codigo [" + codigoInvalido + "] não localizado no banco de dados";
        final int statusCode404 = 404;

        Response response = given()
                .when()
                .get("/pacientes/codigo/{codigo}", codigoInvalido)
                .thenReturn();

        Problema problema = response.as(Problema.class);
        assertEquals(statusCode404, problema.statusCode());
        assertEquals(msgError, problema.mensagem());
    }


    @Test
    @Order(5)
    void testQuandoSalvarPaciente_DeveRetornarStatus201_ETambemOCorpoDaRespostaOsDadosDoPacienteSalvo() {
        int statusCodeEspectativa = 201;

        JSONObject requestParams = new JSONObject();
        requestParams.put("nome", "Diogenes da Silva Santos");
        requestParams.put("numero", "8184768748");
        requestParams.put("bairro", "Alto da Balança");
        requestParams.put("tipoConsulta", "Urologista");
        requestParams.put("status", "Marcado");
        requestParams.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();


        assertEquals(statusCodeEspectativa, response.getStatusCode());
        assertNotNull(response.as(PacienteResponseDTO.class).codigo());
        assertEquals(requestParams.getAsString("nome"), response.as(PacienteResponseDTO.class).nome());
    }

    @Test
    @Order(5)
    void testQuandoSalvarPacienteComCampoNomeNullOuBlank_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteNomeBlank = new JSONObject();
        requestParamsPacienteNomeBlank.put("nome", "   ");
        requestParamsPacienteNomeBlank.put("numero", "8184768748");
        requestParamsPacienteNomeBlank.put("bairro", "Alto da Balança");
        requestParamsPacienteNomeBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteNomeBlank.put("statusCode", "Marcado");
        requestParamsPacienteNomeBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteNomeNull = new JSONObject();
        requestParamsPacienteNomeBlank.put("nome", null);
        requestParamsPacienteNomeBlank.put("numero", "8184768748");
        requestParamsPacienteNomeBlank.put("bairro", "Alto da Balança");
        requestParamsPacienteNomeBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteNomeBlank.put("statusCode", "Marcado");
        requestParamsPacienteNomeBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteNomeBlank.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteNomeNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }


    @Test
    @Order(6)
    void testQuandoSalvarPacienteComCampoNumeroNullOuBlank_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteNumeroBlank = new JSONObject();
        requestParamsPacienteNumeroBlank.put("nome", "DioBotNex");
        requestParamsPacienteNumeroBlank.put("numero", "  ");
        requestParamsPacienteNumeroBlank.put("bairro", "Alto da Balança");
        requestParamsPacienteNumeroBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteNumeroBlank.put("statusCode", "Marcado");
        requestParamsPacienteNumeroBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteNumeroNull = new JSONObject();
        requestParamsPacienteNumeroBlank.put("nome", "DioBotNex");
        requestParamsPacienteNumeroBlank.put("numero", null);
        requestParamsPacienteNumeroBlank.put("bairro", "Alto da Balança");
        requestParamsPacienteNumeroBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteNumeroBlank.put("statusCode", "Marcado");
        requestParamsPacienteNumeroBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteNumeroBlank.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteNumeroNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }

    @Test
    @Order(7)
    void testQuandoSalvarPacienteComCampoBairroNullOuBlank_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteBairroBlank = new JSONObject();
        requestParamsPacienteBairroBlank.put("nome", "DioBotNex");
        requestParamsPacienteBairroBlank.put("numero", "8184768748");
        requestParamsPacienteBairroBlank.put("bairro", "");
        requestParamsPacienteBairroBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteBairroBlank.put("statusCode", "Marcado");
        requestParamsPacienteBairroBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteBairroNull = new JSONObject();
        requestParamsPacienteBairroBlank.put("nome", "DioBotNex");
        requestParamsPacienteBairroBlank.put("numero", "8184768748");
        requestParamsPacienteBairroBlank.put("bairro", null);
        requestParamsPacienteBairroBlank.put("tipoConsulta", "Urologista");
        requestParamsPacienteBairroBlank.put("statusCode", "Marcado");
        requestParamsPacienteBairroBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteBairroBlank.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteBairroNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }

    @Test
    @Order(8)
    void testQuandoSalvarPacienteComCampoTipoConsultaNullOuBlank_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteTipoConsultaBlank = new JSONObject();
        requestParamsPacienteTipoConsultaBlank.put("nome", "DioBotNex");
        requestParamsPacienteTipoConsultaBlank.put("numero", "8184768748");
        requestParamsPacienteTipoConsultaBlank.put("bairro", "Alto da balança");
        requestParamsPacienteTipoConsultaBlank.put("tipoConsulta", " ");
        requestParamsPacienteTipoConsultaBlank.put("statusCode", "Marcado");
        requestParamsPacienteTipoConsultaBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteTipoConsultaNull = new JSONObject();
        requestParamsPacienteTipoConsultaBlank.put("nome", "DioBotNex");
        requestParamsPacienteTipoConsultaBlank.put("numero", "8184768748");
        requestParamsPacienteTipoConsultaBlank.put("bairro", "Alto da balança");
        requestParamsPacienteTipoConsultaBlank.put("tipoConsulta", null);
        requestParamsPacienteTipoConsultaBlank.put("statusCode", "Marcado");
        requestParamsPacienteTipoConsultaBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteTipoConsultaBlank.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteTipoConsultaNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }

    @Test
    @Order(9)
    void testQuandoSalvarPacienteComCampoStatusNullOuBlank_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteStatusBlank = new JSONObject();
        requestParamsPacienteStatusBlank.put("nome", "DioBotNex");
        requestParamsPacienteStatusBlank.put("numero", "8184768748");
        requestParamsPacienteStatusBlank.put("bairro", "Alto da balança");
        requestParamsPacienteStatusBlank.put("tipoConsulta", "Ortopedista");
        requestParamsPacienteStatusBlank.put("statusCode", "                        ");
        requestParamsPacienteStatusBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteStatusNull = new JSONObject();
        requestParamsPacienteStatusBlank.put("nome", "DioBotNex");
        requestParamsPacienteStatusBlank.put("numero", "8184768748");
        requestParamsPacienteStatusBlank.put("bairro", "Alto da balança");
        requestParamsPacienteStatusBlank.put("tipoConsulta", "Oftlamologista");
        requestParamsPacienteStatusBlank.put("statusCode", null);
        requestParamsPacienteStatusBlank.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteStatusBlank.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteStatusNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }


    @Test
    @Order(10)
    void testQuandoSalvarPacienteComCampoDataConsultaNullOuDataPassado_DeveRetornarStatusCode400() {
        int statusCodeEspectativa = 400;

        JSONObject requestParamsPacienteDataConsultaNoPeriodoPassado = new JSONObject();
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("nome", "DioBotNex");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("numero", "8184768748");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("bairro", "Alto da balança");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("tipoConsulta", "Ortopedista");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("statusCode", "                        ");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("dataConsulta", "2020-11-10T18:36:11.428584");

        JSONObject requestParamsPacienteDataConsultaNull = new JSONObject();
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("nome", "DioBotNex");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("numero", "8184768748");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("bairro", "Alto da balança");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("tipoConsulta", "Oftlamologista");
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("statusCode", null);
        requestParamsPacienteDataConsultaNoPeriodoPassado.put("dataConsulta", "2026-11-10T18:36:11.428584");


        Response responseIsBlank = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteDataConsultaNoPeriodoPassado.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        Response responseIsNull = given()
                .contentType(ContentType.JSON)
                .body(requestParamsPacienteDataConsultaNull.toJSONString())
                .when()
                .post("/pacientes")
                .thenReturn();

        assertEquals(statusCodeEspectativa, responseIsBlank.getStatusCode());
        assertEquals(statusCodeEspectativa, responseIsNull.getStatusCode());

    }


    @Test
    @Order(11)
    void testQuandoDeletarPorCodigo_DeveRetornarStatusCode204_EMesmoQueOPacienteNaoExistaOuCodidoInvalidoRetorna204() {
        final int statusCode204 = 204;
        final String codigoExistenteOuNao = "______Pode_Usar_Um_Codigo_Valido_Que_O_Retorno_Vai_Ser_204";
        final JSONObject pacienteRequestDelete = new JSONObject();
        pacienteRequestDelete.put("codigo", codigoExistenteOuNao);


        Response response = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacienteRequestDelete)
                .when()
                .delete("/pacientes")
                .thenReturn();

        assertEquals(statusCode204, response.getStatusCode());

    }


    /*
     * Descrição do comportamento do métod0 atualização parcial, qualquer atributo aonde o código seja válido o campo
     * será atualizado, campos com parâmetros null ou blank não terão alteração, exemplo abaixo só alteramos o campo
     * nome, mas caso queria alterar outros campos e so ler a classe PacienteRequestPatchDTO e fazer o teste de
     * comportamento em outros atributos.
     * */
    @Test
    @Order(12)
    void testQuandoAtualizarParcial_DeveAtualizarOCampoEspecificado_ERetornarOPacienteAtualizadoComOStatusCode200() {
        final int statusCode200 = 200;
        final String codigoExistente = "7ac6865b-397d-4038-8fa8-36855b5de619";
        final String nomeCampoParaSerAtualizadoAtualizacao = "Severino Manoel dos Santos";
        final JSONObject pacientePatch = new JSONObject();
        pacientePatch.put("codigo", codigoExistente);
        pacientePatch.put("nome", nomeCampoParaSerAtualizadoAtualizacao);

        Response response = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePatch)
                .when()
                .patch("/pacientes")
                .thenReturn();

        PacienteResponseDTO pacienteResponseDTO = response.as(PacienteResponseDTO.class);
        assertEquals(statusCode200, response.getStatusCode());
        assertEquals(pacientePatch.getAsString("nome"), pacienteResponseDTO.nome());

    }


    @Test
    @Order(13)
    void testQuandoAtualizarParcialOCodigoForBlankOuNull_DeveLancarExceptionMethodArgumentNotValidExceptionStatusCode400() {
        final int statusCode400 = 400;
        final String codigoNull = null;
        final String codigoInvalido = "  ";
        final String nomeParaAtualizacao = "Severino Manoel dos Santos";

        final JSONObject pacientePatchCodigoBlank = new JSONObject();
        pacientePatchCodigoBlank.put("codigo", codigoInvalido);
        pacientePatchCodigoBlank.put("nome", nomeParaAtualizacao);


        final JSONObject pacientePatchCodigoNull = new JSONObject();
        pacientePatchCodigoNull.put("codigo", codigoNull);
        pacientePatchCodigoNull.put("nome", nomeParaAtualizacao);


        Response responseCodigoBlank = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePatchCodigoBlank)
                .when()
                .patch("/pacientes")
                .thenReturn();


        Response responseCodigoNull = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePatchCodigoNull)
                .when()
                .patch("/pacientes")
                .thenReturn();

        assertEquals(statusCode400, responseCodigoBlank.getStatusCode());
        assertEquals(statusCode400, responseCodigoNull.getStatusCode());
    }


    @Test
    @Order(14)
    void testQuandoAtualizarParcialOCodigoInvalido_DeveLancarRetornarExceptionPacienteNaoLocalizadoCode404() {
        final int statusCode404 = 404;
        final String codigoInvalido = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        final String nomeParaAtualizacao = "Severino Manoel dos Santos";


        final JSONObject pacientePatchCodigoInvalido = new JSONObject();
        pacientePatchCodigoInvalido.put("codigo", codigoInvalido);
        pacientePatchCodigoInvalido.put("nome", nomeParaAtualizacao);

        Response responseCodigoInvalido = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePatchCodigoInvalido)
                .when()
                .patch("/pacientes")
                .thenReturn();

        assertEquals(statusCode404, responseCodigoInvalido.getStatusCode());
        assertEquals(statusCode404, responseCodigoInvalido.getStatusCode());
    }

    @Test
    @Order(15)
    void testQuandoAtualizarPorCompletoPutComCodigoValido_DeveRetornarPacienteAtualizado_EStatusCode200() {
        final int statusCode200 = 200;
        final String codigoValido = "7ac6865b-397d-4038-8fa8-36855b5de619";


        final JSONObject pacientePutCodigoValido = new JSONObject(Map.of(
                "codigo", codigoValido,
                "nome", "Paciente atualizado Pelo Put",
                "numero", "8199999999",
                "bairro", "Rua da rosas",
                "tipoConsulta", "Oftalmologista",
                "status", "Faltou",
                "dataConsulta", "2026-11-10T18:36:11.428584",
                "dataMarcacao", "2026-02-04T17:30:24.970996"

        ));


        Response response = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePutCodigoValido)
                .when()
                .put("/pacientes")
                .thenReturn();


        assertEquals(statusCode200, response.getStatusCode());
        assertEquals("Paciente atualizado Pelo Put", response.as(PacienteResponseDTO.class).nome());

    }


    /*
        Descrição do métod0 atualizar (Atualização por completo), se qualquer campo que esteja a quebrar as regras
        constratint @NotNull ou @NotBlank a exception MethodArgumentNotValidException, retornando o status code 400.
        Leia mais sobre a classe PacienteRequestPutDTO.
     */
    @Test
    @Order(16)
    void testQuandoAtualizarPorCompletoPUTComQualquerCampoInvalido_DeveRetornarMethodArgumentNotValidExceptionStatusCode400() {
        final int statusCode400 = 400;
        final String codigoValido = "7ac6865b-397d-4038-8fa8-36855b5de619";


        final JSONObject pacientePutCodigoValido = new JSONObject(Map.of(
                "codigo", codigoValido,
                //Ausênsia do campo nome(Mas podia ser qualquer campo).
                "numero", "8199999999",
                "bairro", "Rua da rosas",
                "tipoConsulta", "Oftalmologista",
                "status", "Faltou",
                "dataConsulta", "2026-11-10T18:36:11.428584",
                "dataMarcacao", "2026-02-04T17:30:24.970996"

        ));


        Response response = given()
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pacientePutCodigoValido)
                .when()
                .put("/pacientes")
                .thenReturn();

        assertEquals(statusCode400 , response.getStatusCode());

    }

}
