package com.git.devdioge.paciente_api.infraestrutura;

import com.git.devdioge.paciente_api.controller.dto.PacienteRequestPostDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteRequestPatchDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteRequestPutDTO;
import com.git.devdioge.paciente_api.controller.dto.PacienteResponseDTO;
import com.git.devdioge.paciente_api.model.Paciente;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Assembler {


    public static PacienteResponseDTO modelToDTO(Paciente paciente) {
        if (paciente == null) throw new IllegalArgumentException("Impossivel converte, o Paciente está null.");

        return new PacienteResponseDTO(paciente.getCodigo(), paciente.getNome(),
                paciente.getNumero(), paciente.getBairro(),
                paciente.getTipoConsulta(), paciente.getStatus(),
                paciente.getDataConsulta(), paciente.getDataMarcacao());
    }


    public static List<PacienteResponseDTO> listModelTolistDTO(List<Paciente> pacientesList) {
        if (pacientesList.isEmpty()) {
            return List.of();
        }
        return pacientesList.stream().map(Assembler::modelToDTO).toList();

    }


    public static Paciente dtoPostToModel(PacienteRequestPostDTO requestDTO) {
        return new Paciente.Builder()
                .setNome(requestDTO.nome())
                .setStatus(requestDTO.status())
                .setBairro(requestDTO.bairro())
                .setNumero(requestDTO.numero())
                .setTipoConsulta(requestDTO.tipoConsulta())
                .setDataConsulta(requestDTO.dataConsulta())
                .build();

    }

    public static Paciente dtoPutToModel(PacienteRequestPutDTO requestPutDTO) {
        return new Paciente.Builder()
                .setCodigo(requestPutDTO.codigo())
                .setNome(requestPutDTO.nome())
                .setStatus(requestPutDTO.status())
                .setBairro(requestPutDTO.bairro())
                .setNumero(requestPutDTO.numero())
                .setTipoConsulta(requestPutDTO.tipoConsulta())
                .setDataConsulta(requestPutDTO.dataConsulta())
                .build();

    }

    public static Paciente dtoPatchToModel(PacienteRequestPatchDTO pacienteRequestPatchDTO) {
        return new Paciente.Builder()
                .setCodigo(pacienteRequestPatchDTO.codigo())
                .setNome(pacienteRequestPatchDTO.nome())
                .setStatus(pacienteRequestPatchDTO.status())
                .setBairro(pacienteRequestPatchDTO.bairro())
                .setNumero(pacienteRequestPatchDTO.numero())
                .setTipoConsulta(pacienteRequestPatchDTO.tipoConsulta())
                .setDataConsulta(pacienteRequestPatchDTO.dataConsulta())
                .patchBuild();
    }
}
