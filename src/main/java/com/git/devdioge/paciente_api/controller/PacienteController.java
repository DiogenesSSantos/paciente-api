package com.git.devdioge.paciente_api.controller;

import com.git.devdioge.paciente_api.controller.dto.*;
import com.git.devdioge.paciente_api.docs.PacienteControllerSwaggerDocs;
import com.git.devdioge.paciente_api.infraestrutura.Assembler;
import com.git.devdioge.paciente_api.model.Paciente;
import com.git.devdioge.paciente_api.service.IPacienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pacientes")
public class PacienteController implements PacienteControllerSwaggerDocs {

    private IPacienteService service;

	public PacienteController(IPacienteService service) {
        this.service = service;
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> salvar(@RequestBody PacienteRequestPostDTO pacienteRequestPostDTO) {
        Paciente paciente = Assembler.dtoPostToModel(pacienteRequestPostDTO);
        paciente = service.salvar(paciente);
        PacienteResponseDTO pacienteResponseDTO = Assembler.modelToDTO(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponseDTO);
    }


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PacienteResponseDTO>> buscarTodos() {
        List<Paciente> listPacientes = service.buscarTodos();
        List<PacienteResponseDTO> pacienteResponseDTOS = Assembler.listModelTolistDTO(listPacientes);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTOS);
	}

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable(value = "id")  Long id){
        Paciente paciente = service.buscarPorId(id);
        PacienteResponseDTO pacienteResponseDTO = Assembler.modelToDTO(paciente);

        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTO);
    }


    @GetMapping(value = "/codigo/{codigo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> buscarPorCodigo(@PathVariable(value = "codigo") String codigo) {
        Paciente paciente = service.buscarPorCodigo(codigo);
        PacienteResponseDTO pacienteResponseDTO = Assembler.modelToDTO(paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTO);
    }


    @DeleteMapping()
    public ResponseEntity<Void> deletar(@RequestBody PacienteRequestDeleteDTO requestDTO) {
        service.deletarPorCodigo(requestDTO.codigo());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> atualizarParcial(@RequestBody @Valid PacienteRequestPatchDTO
                                                                            pacienteRequestPatchDTO) {
        Paciente pacientePatch = Assembler.dtoPatchToModel(pacienteRequestPatchDTO);
        Paciente paciente = service.atualizar(pacientePatch.getCodigo(), pacientePatch);
        PacienteResponseDTO pacienteResponseDTO = Assembler.modelToDTO(paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTO);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PacienteResponseDTO> atualizar(@RequestBody @Valid PacienteRequestPutDTO pacienteRequestPutDTO) {
        Paciente pacientePUT = Assembler.dtoPutToModel(pacienteRequestPutDTO);
        Paciente paciente = service.atualizar(pacientePUT.getCodigo(), pacientePUT);
        PacienteResponseDTO pacienteResponseDTO = Assembler.modelToDTO(paciente);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponseDTO);
    }

}
