package com.git.devdioge.paciente_api;

import com.git.devdioge.paciente_api.config.TestConfigs;
import com.git.devdioge.paciente_api.controller.PacienteController;
import com.git.devdioge.paciente_api.controller.dto.PacienteRequestDeleteDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteRequestPatchDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteRequestPutDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteResponseDTO;
import com.git.devdioge.paciente_api.exception.PacienteNaoLocalizadoException;
import com.git.devdioge.paciente_api.exceptionhandler.Problema;
import com.git.devdioge.paciente_api.model.Paciente;
import com.git.devdioge.paciente_api.service.IPacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    private Paciente paciente;
    private List<Paciente> pacienteList = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private IPacienteService pacienteService;

    @BeforeEach
    void setup() {
        paciente = new Paciente.Builder()
                .setNome("Maria josé")
                .setNumero("81984768748")
                .setBairro("Alto da balança")
                .setStatus("Marcado")
                .setTipoConsulta("Ortopedista")
                .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00, 00))
                .build();


        pacienteList.addAll(List.of(
                new Paciente.Builder()
                        .setNome("Severino Manoel do Santos")
                        .setNumero("81999999999")
                        .setBairro("Alto da balança")
                        .setStatus("Aguardando")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                        .build(),
                new Paciente.Builder()
                        .setNome("Diego da Silva Santos")
                        .setNumero("81988888888")
                        .setBairro("Alto da balança")
                        .setStatus("Marcado")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                        .build(),

                new Paciente.Builder()
                        .setNome("Diogenes da Silva Santos")
                        .setNumero("81984768748")
                        .setBairro("Alto da balança")
                        .setStatus("Marcado")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                        .build(),

                new Paciente.Builder()
                        .setNome("Danilo da Silva Santos")
                        .setNumero("81977777777")
                        .setBairro("Alto da balança")
                        .setStatus("Marcado")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                        .build(),
                paciente));
    }


    @Test
    void testQuandoBuscarTodos_DeveRetornarUmaListaDePaciente() throws Exception {
        given(pacienteService.buscarTodos()).willReturn(pacienteList);

        ResultActions response = mockMvc.perform(get("/pacientes"));
        String responseBody = response.andReturn().getResponse().getContentAsString();
        List<PacienteResponseDTO> listpacienteResponseDTO = mapper.readValue(responseBody, new TypeReference<List<PacienteResponseDTO>>() {
        });


        response.andDo(print())
                .andExpect(status().isOk());
        assertEquals(5, listpacienteResponseDTO.size(), () -> "Esperava uma lista populada com 5 Pacientes.");

    }


    @Test
    void testQuandoSalvarOPaciente_DeveRetornarStatusCode201_EOPacienteCriado() throws Exception {
        given(pacienteService.salvar(any(Paciente.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));


        ResultActions response = mockMvc.perform(post("/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(paciente)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").isNotEmpty())
                .andExpect(jsonPath("$.nome").value(paciente.getNome()))
                .andExpect(jsonPath("$.numero").value(paciente.getNumero()))
                .andExpect(jsonPath("$.bairro").value(paciente.getBairro()))
                .andExpect(jsonPath("$.tipoConsulta").value(paciente.getTipoConsulta()))
                .andExpect(jsonPath("$.status").value(paciente.getStatus()))
                .andExpect(jsonPath("$.dataConsulta").value("2026-05-22T13:00:00"));
    }

    @Test
    void testQuandoSalvarOPacienteComCampoNomeNullOuBlank_DeveRetornarStatus400() throws Exception {
        int statusCode = 400;
        String campoNomeInvalido = "Campo código obrigatória está null ou vázio";
        given(pacienteService.salvar(any(Paciente.class))).willThrow(new IllegalArgumentException(campoNomeInvalido));

        ResultActions response = mockMvc.perform(post("/pacientes")
                .contentType("application/json")
                .content(mapper.writeValueAsString(paciente)));


        response.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(statusCode))
                .andExpect(jsonPath("$.mensagem").value(campoNomeInvalido));

    }


    @Test
    void testQuandobuscarPorId_DeveRetornaOPacienteEoStatusCode200() throws Exception {
        long idExpectativa = 1;
        given(pacienteService.buscarPorId(idExpectativa)).willReturn(paciente);

        ResultActions response = mockMvc.perform(get("/pacientes/{id}", idExpectativa));


        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(paciente.getNome()));
    }


    @Test
    void testQuandoBuscarPorIDNaoExistente_DeveRetornaExceptionPacienteNaolocalizadoStatusCode404() throws Exception {
        long idNaoExistente = 897;
        String mensagemExpectativa = "Paciente de id [" + idNaoExistente + "] não localizado no banco de dados";
        given(pacienteService.buscarPorId(idNaoExistente))
                .willThrow(new PacienteNaoLocalizadoException(mensagemExpectativa));


        ResultActions response = mockMvc.perform(get("/pacientes/{id}", idNaoExistente)
                .contentType(TestConfigs.CONTENT_TYPE_JSON));


        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    var renponseBody = result.getResponse().getContentAsString();
                    Problema problema = mapper.readValue(renponseBody, Problema.class);
                    problema.mensagem().equalsIgnoreCase(mensagemExpectativa);
                });

    }


    @Test
    void testQuandoDeletarPorCodigoMesmoComCodigoInexiste_DeveRetornarNenhumConteudoStatusCode204() throws Exception {
        String codigo = "b8226bc9-d733-449e-82d3-330067a51603";
        willDoNothing().given(pacienteService).deletarPorCodigo(codigo);
        PacienteRequestDeleteDTO requestDto = new PacienteRequestDeleteDTO(codigo);


        ResultActions response = mockMvc.perform(delete("/pacientes")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .content(mapper.writeValueAsString(requestDto)));


        response.andDo(print())
                .andExpect(status().isNoContent());

        then(pacienteService).should().deletarPorCodigo(codigo);
    }

    @Test
    void testQuandoAtualizarParcial_DeveRetornarOPacienteAtualizadoComSeusCamposStatusCode200() throws Exception {
        String codigoPaciente = "b8226bc9-d733-449e-82d3-330067a51603";

        // DTO que o controller espera (inclui codigo)
        PacienteRequestPatchDTO patchDto = new PacienteRequestPatchDTO(
                codigoPaciente,
                "Diogenes",
                null, null, null, null,
                LocalDateTime.of(2026, 5, 1, 8, 22, 0),
                null);

        // entidade que o service deve retornar (paciente atualizado)
        Paciente pacienteAtualizado = new Paciente.Builder()
                .setCodigo(codigoPaciente)
                .setNome("Diogenes")
                .setNumero("81984768748") // ou valores do seu 'paciente' base
                .setBairro("Alto da balança")
                .setTipoConsulta("Ortopedista")
                .setStatus("Marcado")
                .setDataConsulta(patchDto.dataConsulta())
                .build();

        given(pacienteService.atualizar(eq(codigoPaciente), any(Paciente.class)))
                .willReturn(pacienteAtualizado);

        ResultActions response = mockMvc.perform(patch("/pacientes")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .content(mapper.writeValueAsString(patchDto)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigoPaciente))
                .andExpect(jsonPath("$.nome").value("Diogenes"));

        then(pacienteService).should().atualizar(eq(codigoPaciente), any(Paciente.class));
    }


    @Test
    void testQuandoAtualizarPorCompleto_DeveRetornarPacienteAtualizadoComStatusCode200() throws Exception {
        String codigoPaciente = "b8226bc9-d733-449e-82d3-330067a51603";
        var pacientePut = new PacienteRequestPutDTO(codigoPaciente,
                "Diogenes",
                "817788454",
                "Diogenes",
                "Orotpedia",
                "Ok",
                LocalDateTime.of(2026,2,14,22,33),
                LocalDateTime.of(2026,2,14,22,33));

        given(pacienteService.atualizar(eq(codigoPaciente), any(Paciente.class)))
                .willReturn(paciente);


        ResultActions response = mockMvc.perform(put("/pacientes")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .content(mapper.writeValueAsString(pacientePut)));

        response.andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    void testQuandoAtualizarPorCompletoExistAlgumCampoInvalido_DeveLancarMethodArgumentNotValidExceptionStatusCode400()
            throws Exception {
        var pacientePut = new PacienteRequestPutDTO(null,
                "Diogenes",
                "817788454",
                null,
                "Orotpedia",
                "Ok",
                LocalDateTime.of(2026,2,14,22,33),
                LocalDateTime.of(2026,2,14,22,33));

        ResultActions response = mockMvc.perform(put("/pacientes")
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .content(mapper.writeValueAsString(pacientePut)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void testQuandoBuscarPorCodigo_DeveRetornarOPacienteComCodigoEspecificado_EStatusCode200() throws Exception {
        final String codigoValido = "b8226bc9-d733-449e-82d3-330067a51603";

        given(pacienteService.buscarPorCodigo(codigoValido)).willReturn(paciente);


        ResultActions response = mockMvc.perform(get("/pacientes/codigo/{codigo}", codigoValido));


        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(paciente.getNome()));

    }


    @Test
    void testQuandoBuscarPorCodigoInexistene_DeveRetornarOPacienteComCodigoEspecificado_EStatusCode200() throws Exception {
        final String codigoValido = "codigo_inexistente";

        given(pacienteService.buscarPorCodigo(codigoValido)).willThrow(
                new PacienteNaoLocalizadoException("Msg padrão service."));


        ResultActions response = mockMvc.perform(get("/pacientes/codigo/{codigo}", codigoValido));


        response.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").isNotEmpty());

    }

}
