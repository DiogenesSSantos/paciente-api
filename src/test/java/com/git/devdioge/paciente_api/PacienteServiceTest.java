package com.git.devdioge.paciente_api;

import com.git.devdioge.paciente_api.exception.PacienteNaoLocalizadoException;
import com.git.devdioge.paciente_api.model.Paciente;
import com.git.devdioge.paciente_api.model.PacienteFiltro;
import com.git.devdioge.paciente_api.repository.IPacienteRepository;
import com.git.devdioge.paciente_api.service.PacienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {
    private Paciente paciente;
    private List<Paciente> listPacientes = new ArrayList<>();


    @Mock
    private IPacienteRepository repository;
    @InjectMocks
    private PacienteService service;

    @BeforeEach
    void setup() {
        paciente = new Paciente.Builder()
                .setNome("Maria josé")
                .setNumero("81984768748")
                .setBairro("Alto da balança")
                .setStatus("Marcado")
                .setTipoConsulta("Ortopedista")
                .setDataConsulta(LocalDateTime.of(2026, 5, 22, 13, 00))
                .build();


        listPacientes.addAll(List.of(
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
                        .setDataConsulta(LocalDateTime.of(2026 , 5,22,13,00))
                        .build()
        ));
    }


    @Test
    void testQuandoBuscarTodos_DeveRetornarListaPacienteComDadosOuUmaListaPacienteVazia() {
        given(repository.buscarTodos()).willReturn(List.of(paciente));
        List<Paciente> pacienteList;

        pacienteList = service.buscarTodos();


        then(repository)
                .should().buscarTodos();
        assertFalse(pacienteList.isEmpty());
        assertEquals(paciente.getCodigo(), pacienteList.getFirst().getCodigo());

    }

    @Test
    void testQuandoSalvarPaciente_DeveRetornarPacientePersistidoEComId() {
        Paciente pacienteExpectativa;
        given(repository.save(any(Paciente.class))).willAnswer(
                invocation -> invocation.getArgument(0));

        pacienteExpectativa = service.salvar(paciente);

        assertNotNull(paciente);
        assertEquals(pacienteExpectativa.getCodigo(), paciente.getCodigo());

    }


    @Test
    void testQuandoBuscarPorId_DeveRetornarOPacienteDoBancoDeDadosDoIdCorrespondido() {
        Long expectativaId = 27L;
        given(repository.buscarPorId(expectativaId)).willReturn(Optional.of(paciente));

        var optionalPaciente = service.buscarPorId(expectativaId);


        assertNotNull(optionalPaciente, () -> "O paciente buscado não existe no banco de dados, seu valor retornado é null");
        assertEquals(optionalPaciente.getCodigo(), paciente.getCodigo());

    }

    @Test
    void testQuandoBuscarPorIdInexistente_DeveLancarPacienteNaoLocalizadoException() {
        Long idNaoExistente = 100000L;
        String expectativaMensagem = String.format("Paciente de id [%d] não localizado no banco de dados ",
                idNaoExistente);

        given(repository.buscarPorId(idNaoExistente)).willThrow(new PacienteNaoLocalizadoException(
                String.format("Paciente de id [%d] não localizado no banco de dados ", idNaoExistente)));

        PacienteNaoLocalizadoException pacienteNaoLocalizadoException = assertThrows(
                PacienteNaoLocalizadoException.class,
                () -> service.buscarPorId(idNaoExistente), () ->
                        "Expectativa era lançar uma exception PacienteNaoLocalizadoException, mas não foi lançada");

        assertNotNull(pacienteNaoLocalizadoException);
        assertEquals(expectativaMensagem, pacienteNaoLocalizadoException.getMessage());
    }


    @Test
    void testQuandoFiltraPorNome_DeveRetornarListaDePacienteQuePossuaONomeCorrespondido() {
        PacienteFiltro pacienteFiltro = new PacienteFiltro("S");
        given(repository.buscarPorNome(pacienteFiltro.nome())).willReturn(
                listPacientes.stream()
                        .filter(p -> p.getNome().toUpperCase().startsWith(pacienteFiltro.nome().toUpperCase()))
                        .toList());

        List<Paciente> pacienteList = service.buscarPorNome(pacienteFiltro.nome());

        assertFalse(pacienteList.isEmpty());
        assertNotNull(pacienteList);
        assertTrue(pacienteList.stream()
                .allMatch(p1 -> p1.getNome().toUpperCase()
                        .startsWith(pacienteFiltro.nome().toUpperCase())));
    }

    @Test
    void testQuandoFiltraPorNomeSeONomeForNullOuEmBranco_DeveRetornarListaDeTodosPacienteOuUmaListaVazia() {
        PacienteFiltro pacienteFiltroIsBlank = new PacienteFiltro("   ");
        PacienteFiltro pacienteFiltroNull = new PacienteFiltro(null);
        given(repository.buscarPorNome(pacienteFiltroIsBlank.nome())).willReturn(List.of()).willReturn(List.of());

        List<Paciente> pacienteListFiltroIsBlank = service.buscarPorNome(pacienteFiltroIsBlank.nome());
        List<Paciente> pacienteListFiltroIsNull = service.buscarPorNome(pacienteFiltroNull.nome());

        assertTrue(pacienteListFiltroIsBlank.isEmpty());
        assertTrue(pacienteListFiltroIsNull.isEmpty());



    }

    @RepeatedTest(3)
    void testQuandoAtualizarOPacienteComCodigo_DeveRetornaOUsuarioComOCampoAtualizado() {
        String codigoPacienteBD = "d385b86d-a99c-456a-8ebd-ad2e903b5b50";
        Paciente pacienteExpectativa;
        StringBuilder stb = geraNumeroCelular();
        given(repository.buscarPorCodigo(codigoPacienteBD)).willReturn(Optional.of(paciente));
        given(repository.save(any(Paciente.class))).willAnswer(invocation -> invocation.getArgument(0));

        pacienteExpectativa = service.buscarPorCodigo(codigoPacienteBD);
        pacienteExpectativa.setNumero(stb.toString());
        pacienteExpectativa = service.atualizar(codigoPacienteBD, pacienteExpectativa);

        assertNotNull(codigoPacienteBD, pacienteExpectativa.getCodigo());
        assertEquals(stb.toString(), pacienteExpectativa.getNumero());
    }

    private static StringBuilder geraNumeroCelular() {
        StringBuilder stb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            stb.append(random.nextInt(10));
        }
        return stb;
    }

    @RepeatedTest(3)
    void testQuandoAtualizarOPacienteComCodigoErrado_DeveLancarPacienteNaoLocalizadoException() {
        String codigoPacienteErrado = "d385b86d-a99c-456a-8ebd-ad2e903b5b";
        given(repository.buscarPorCodigo(codigoPacienteErrado)).willThrow(
                new PacienteNaoLocalizadoException(
                        String.format("Paciente com código [%s] não localizado no Banco de dados.",
                                codigoPacienteErrado)));

        assertThrows(PacienteNaoLocalizadoException.class,
                () -> service.buscarPorCodigo(codigoPacienteErrado),
                "Execução foi um sucesso, e se esperava uma exception PacienteNaoLocalizadoException.");


    }


    @RepeatedTest(5)
    void testQuandoDeletarPacienteComCodigoValido_DeveReturnVoid() {
        String codigoPacienteBD = "d385b86d-a99c-456a-8ebd-ad2e903b5b50";
        willDoNothing().given(repository).delete(any(Paciente.class));
        given(repository.buscarPorCodigo(codigoPacienteBD)).willReturn(Optional.of(paciente));

        service.deletarPorCodigo(codigoPacienteBD);
        then(repository).should()
                .delete(any(Paciente.class));
    }
}
