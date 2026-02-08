package com.git.devdioge.paciente_api;


import com.git.devdioge.paciente_api.controller.dto.PacienteResponseDTO;
import com.git.devdioge.paciente_api.exception.DataPassadaException;
import com.git.devdioge.paciente_api.infraestrutura.Assembler;
import com.git.devdioge.paciente_api.model.Paciente;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class PacienteUnitarioTest {

    private Paciente paciente;

    @BeforeEach
    void configuracao(){
        paciente = new Paciente.Builder()
                .setNome("Diogenes")
                .setNumero("8184768748")
                .setBairro("Alto da balança")
                .setTipoConsulta("Ortopedista")
                .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                        22,00,0))
                .setStatus("Aguardando")
                .build();

    }

    @Test
    void testQuandoCriarPaciente_DeveTerTodosOsCamposNaoNull(){
        //Given
        Paciente paciente = this.paciente;
        //When  & Then
        assertAll(
                () -> assertNotNull(paciente.getNome()),
                () -> assertNotNull(paciente.getNumero()),
                () -> assertNotNull(paciente.getBairro()),
                () -> assertNotNull(paciente.getCodigo()),
                () -> assertNotNull(paciente.getStatus()),
                () -> assertNotNull(paciente.getDataConsulta()),
                () -> assertNotNull(paciente.getDataMarcacao()),
                () -> assertNotNull(paciente.getTipoConsulta())
        );
    }

    @RepeatedTest(5)
    void testQuandoCampoNomeTiverNullOuBlank_DeveLancarIllegaArgumentException(){
        String expectativaMsgCampoNull = "Campo nome obrigatória está null ou vázio";
        String expectativaMsgCampoBlank = "Campo nome obrigatória está null ou vázio";

        //when
        IllegalArgumentException illegalArgumentExceptionCampoNull = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome(null)
                        .setNumero("8184768748")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");

        IllegalArgumentException illegalArgumentExceptionCampoBlank = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("     ")
                        .setNumero("8184768748")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");
        //then
        assertEquals(expectativaMsgCampoNull, illegalArgumentExceptionCampoNull.getMessage());
        assertEquals(expectativaMsgCampoBlank, illegalArgumentExceptionCampoBlank.getMessage());
    }



    @RepeatedTest(5)
    void testQuandoCampoNumeroTiverNullOuBlank_DeveLancarIllegaArgumentException(){
        String expectativaMsgCampoNull = "Campo número obrigatória está null ou vázio";
        String expectativaMsgCampoBlank = "Campo número obrigatória está null ou vázio";

        //when
        IllegalArgumentException illegalArgumentExceptionCampoNull = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero(null)
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");

        IllegalArgumentException illegalArgumentExceptionCampoBlank = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("        ")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");
        //then
        assertEquals(expectativaMsgCampoNull, illegalArgumentExceptionCampoNull.getMessage());
        assertEquals(expectativaMsgCampoBlank, illegalArgumentExceptionCampoBlank.getMessage());
    }

    @RepeatedTest(5)
    void testQuandoCampoBairroTiverNullOuBlank_DeveLancarIllegaArgumentException(){
        String expectativaMsgCampoNull = "Campo bairro obrigatória está null ou vázio";
        String expectativaMsgCampoBlank = "Campo bairro obrigatória está null ou vázio";

        //when
        IllegalArgumentException illegalArgumentExceptionCampoNull = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("999999999")
                        .setBairro(null)
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");

        IllegalArgumentException illegalArgumentExceptionCampoBlank = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("99999999")
                        .setBairro("        ")
                        .setTipoConsulta("Ortopedista")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");
        //then
        assertEquals(expectativaMsgCampoNull, illegalArgumentExceptionCampoNull.getMessage());
        assertEquals(expectativaMsgCampoBlank, illegalArgumentExceptionCampoBlank.getMessage());
    }

    @RepeatedTest(5)
    void testQuandoCampoTipoConsultaTiverNullOuBlank_DeveLancarIllegaArgumentException(){
        String expectativaMsgCampoNull = "Campo tipo_Consulta obrigatória está null ou vázio";
        String expectativaMsgCampoBlank = "Campo tipo_Consulta obrigatória está null ou vázio";

        //when
        IllegalArgumentException illegalArgumentExceptionCampoNull = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("999999999")
                        .setBairro("Alto da balança")
                        .setTipoConsulta(null)
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");

        IllegalArgumentException illegalArgumentExceptionCampoBlank = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("99999999")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("          ")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");
        //then
        assertEquals(expectativaMsgCampoNull, illegalArgumentExceptionCampoNull.getMessage());
        assertEquals(expectativaMsgCampoBlank, illegalArgumentExceptionCampoBlank.getMessage());
    }

    @RepeatedTest(5)
    void testQuandoCampoStatusTiverNullOuBlank_DeveLancarIllegaArgumentException(){
        String expectativaMsgCampoNull = "Campo statusCode statusCode está null ou vázio";
        String expectativaMsgCampoBlank = "Campo statusCode statusCode está null ou vázio";

        //when
        IllegalArgumentException illegalArgumentExceptionCampoNull = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("999999999")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedia")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus(null)
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");

        IllegalArgumentException illegalArgumentExceptionCampoBlank = assertThrows(IllegalArgumentException.class,
                () -> new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("99999999")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedia")
                        .setDataConsulta(LocalDateTime.of(2027 , 05,5,2,
                                22,00,0))
                        .setStatus("                  ")
                        .build(),
                () -> "A criação do usuário não gerou a IllegalArgument Exeception!");
        //then
        assertEquals(expectativaMsgCampoNull, illegalArgumentExceptionCampoNull.getMessage());
        assertEquals(expectativaMsgCampoBlank, illegalArgumentExceptionCampoBlank.getMessage());
    }


    @RepeatedTest(5)
    void testQuandoDataDaConsultaForAtribuidaUmDataPassada_DeveLancarDataPassadaException(){
        String msg = "Data da consulta errada, precisa ser no futuro";

        DataPassadaException dataPassadaException = assertThrows(DataPassadaException.class,
                ()->    new Paciente.Builder()
                        .setNome("Diogenes")
                        .setNumero("99999999")
                        .setBairro("Alto da balança")
                        .setTipoConsulta("Ortopedia")
                        .setDataConsulta(LocalDateTime.of(2025 , 05,5,2,
                                22,00,0))
                        .setStatus("Aguardando")
                        .build(), ()-> "");

        assertNotNull(dataPassadaException);
        assertEquals(msg, dataPassadaException.getMessage());
    }


    @Test
    void testQuandoParametroDoMetodoStaticAssemblerModelToDTOForNull_DeveLancarIllegalArgumentException() {
        Paciente pacienteNull = null;

        assertThrows(IllegalArgumentException.class, ()->
                Assembler.modelToDTO(pacienteNull) ,
                ()-> "Esperava uma exception IllegalArgumentsException, mas não foi lançada.");
    }

    @Test
    void testQuandoParametroDoMetodoStaticAssemblerlistModelTolistDTOForNull_DeveLancarIllegalArgumentException() {
        List<Paciente> pacienteListNull = List.of();
        List<PacienteResponseDTO> pacienteResponseDTOS = Assembler.listModelTolistDTO(pacienteListNull);
        assertTrue(pacienteResponseDTOS.isEmpty());
    }
}
