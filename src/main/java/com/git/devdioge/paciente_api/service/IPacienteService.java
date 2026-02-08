package com.git.devdioge.paciente_api.service;

import com.git.devdioge.paciente_api.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IPacienteService {

    Paciente salvar(Paciente paciente);
    List<Paciente> buscarPorNome(String nome);
    Paciente buscarPorId(Long id);
    Paciente buscarPorCodigo(String codigo);
    List<Paciente> buscarTodos();
    void deletarPorCodigo(String codigoPacienteBD);
    Paciente atualizar(String codico, Paciente paciente);
}
