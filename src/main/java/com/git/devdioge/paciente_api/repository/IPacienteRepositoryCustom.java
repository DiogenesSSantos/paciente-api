package com.git.devdioge.paciente_api.repository;

import com.git.devdioge.paciente_api.model.Paciente;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPacienteRepositoryCustom {

    Optional<Paciente> buscarPorCodigo(String codigo);
    Optional<Paciente> buscarPorId(Long id);
    List<Paciente> buscarTodos();
    List<Paciente> buscarPorNome(String nome);
}
