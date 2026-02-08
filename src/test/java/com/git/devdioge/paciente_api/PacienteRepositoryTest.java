package com.git.devdioge.paciente_api;


import com.git.devdioge.paciente_api.config.AbstractIntegration;
import com.git.devdioge.paciente_api.exception.PacienteNaoLocalizadoException;
import com.git.devdioge.paciente_api.model.Paciente;
import com.git.devdioge.paciente_api.repository.IPacienteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PacienteRepositoryTest extends AbstractIntegration {

    private Paciente paciente;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @BeforeAll
    void setup() {
        paciente = new Paciente.Builder()
                .setNome("Mariquinha da Carmo")
                .setNumero("81999999999")
                .setBairro("Sitio do meio")
                .setTipoConsulta("Oftalmologista")
                .setStatus("Faltou")
                .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                .build();


        pacienteRepository.saveAll(List.of(
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
                paciente
        ));
    }


    @Test
    void testQuandoBuscarTodos_DeveRetornarListaComTodosPacientes() {
        List<Paciente> pacienteList = pacienteRepository.buscarTodos();
        assertFalse(pacienteList.isEmpty());
    }

    @Test
    void testQuandoBuscarPorId1_DeveRetornaOPacienteComIdCorrespondido(){
        long idExpectativa = 1;
        Paciente pacienteLocalizado;
        String nomeExpectativa = "Severino Manoel do Santos";

        pacienteLocalizado = pacienteRepository.buscarPorId(idExpectativa).orElseGet(()-> null);

        assertNotNull(pacienteLocalizado, ()-> "Se esperava um paciente, mas retornou null.");
        assertTrue(pacienteLocalizado.getNome().equalsIgnoreCase(nomeExpectativa),
                ()-> String.format("Se esperava paciente com nome [%s], mas foi retornado nome [%s]. "
                        , nomeExpectativa, pacienteLocalizado.getNome()));
    }


    @Test
    void testQuandoBuscarPorIdQueNaoExiste_DeveLancarExceptionPacienteNaoLocalizado() {
        long idNaoExisteExpectativa = 120000l;


        assertThrows(PacienteNaoLocalizadoException.class,
                ()-> pacienteRepository.buscarPorId(idNaoExisteExpectativa).orElseThrow(
                        ()-> new PacienteNaoLocalizadoException("Paciente não localizado.")),
                ()-> "Se esperava Exception PacienteNaoLocalidoException mas não foi retornada. "
                );

    }


    @Test
    void testQuandoBuscarPorNomeInicialD_DeveRetornarTodosPacienteQueComecaComALetraD() {
        String nomeExpectativa = "D";
        List<Paciente> pacientesListaExpectativa;

        pacientesListaExpectativa = pacienteRepository.buscarPorNome(nomeExpectativa);

        assertTrue(pacientesListaExpectativa.stream()
                .allMatch(paciente -> paciente.getNome().startsWith(nomeExpectativa)),
                ()-> "Esperou que todos pacientes começassem com a letra ["+nomeExpectativa+"] " +
                        "mas não foi isso que ocorreu.");

        assertEquals(3, pacientesListaExpectativa.size());
    }


    @Test
    void testQuandoBuscarPorNomeNaoExistirPaciente_DeveRetornaListaVazia() {
        String nomeNaoExistente = "çppaoq";
        List<Paciente> pacienteList = pacienteRepository.buscarPorNome(nomeNaoExistente);
        assertTrue(pacienteList.isEmpty());

    }

    @Test
    void testQuandoBuscarPorNomeComParametroNullOuBlank_DeveRetornaListaTodosPacienteOuListaVazia() {
        String nomeIsBlank = "     ";
        String nomeIsNull = null;
        List<Paciente> pacienteListIsBlank;
        List<Paciente> pacienteListIsNull;

        pacienteListIsBlank = pacienteRepository.buscarPorNome(nomeIsBlank);
        pacienteListIsNull = pacienteRepository.buscarPorNome(nomeIsNull);

        assertNotNull(pacienteListIsBlank);
        assertNotNull(pacienteListIsNull);
    }

    @Test
    void testQuandoBuscarPorCodigo_DeveRetornaOPacienteCorrespondidoPeloCodigo() {
        String codigoExpectativa = paciente.getCodigo();
        Paciente pacienteExpectativa;

        pacienteExpectativa = pacienteRepository.buscarPorCodigo(codigoExpectativa).orElseGet(()-> null);

        assertNotNull(pacienteExpectativa, ()-> "Esperava o paciente, mas retornou valor null.");
        assertEquals(codigoExpectativa, pacienteExpectativa.getCodigo(), ()-> "Os codigo não se corresponde.");
        assertEquals("Mariquinha da Carmo", pacienteExpectativa.getNome(), ()-> "Os nome não se corresponde.");
    }


    @Test
    void testQuandoBuscarPorCodigoInexistente_DeveLancarExceptionPacienteNaoLocalizado() {
        String codigoNaoExiste = "codigo_nao_existe";

        assertThrows(PacienteNaoLocalizadoException.class,
                ()-> pacienteRepository.buscarPorCodigo(codigoNaoExiste).orElseThrow(()->
                        new PacienteNaoLocalizadoException("")),
                ()-> "Se esperava a expection PacienteNaoLocalizadoException, mas não foi retornada.");

    }


}
