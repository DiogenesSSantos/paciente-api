package com.git.devdioge.paciente_api.service;

import com.git.devdioge.paciente_api.exception.PacienteNaoLocalizadoException;
import com.git.devdioge.paciente_api.model.Paciente;
import com.git.devdioge.paciente_api.repository.IPacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class PacienteService implements IPacienteService {

    private IPacienteRepository repository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.repository = pacienteRepository;
    }

    @Override
    public List<Paciente> buscarTodos() {
        return repository.buscarTodos();
    }

    @Override
    public List<Paciente> buscarPorNome(String nome) {
        List<Paciente> pacienteLista = repository.buscarPorNome(nome);

        return pacienteLista;
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return repository.buscarPorId(id).
                orElseThrow(() -> new PacienteNaoLocalizadoException(
                        "Paciente de id [" + id + "] não localizado no banco de dados "));
    }

    @Override
    public Paciente buscarPorCodigo(String codigo) {
        return repository.buscarPorCodigo(codigo).
                orElseThrow(() -> new PacienteNaoLocalizadoException(
                        "Paciente de codigo [" + codigo + "] não localizado no banco de dados"));
    }

    @Override
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }


    @Override
    public Paciente atualizar(String codigo, Paciente paciente) {
        var pacientePersistido = buscarPorCodigo(codigo);

        atualizarAtributosPaciente(pacientePersistido, paciente);
        pacientePersistido = salvar(pacientePersistido);
        return pacientePersistido;
    }

    @Override
    public void deletarPorCodigo(String codigo) {
        repository.buscarPorCodigo(codigo).ifPresentOrElse(
                paciente -> repository.delete(paciente),
                () -> log.warn("Paciente com codigo [" + codigo + "] não existe no banco de dados.")
        );
    }

    private Paciente atualizarAtributosPaciente(Paciente pacientePersistido, Paciente pacienteAtualizado) {
        if (pacienteAtualizado.getNome() != null && !pacienteAtualizado.getNome().isBlank()) {
            pacientePersistido.setNome(pacienteAtualizado.getNome());
        }

        if (pacienteAtualizado.getNumero() != null && !pacienteAtualizado.getNumero().isBlank()) {
            pacientePersistido.setNumero(pacienteAtualizado.getNumero());
        }

        if (pacienteAtualizado.getBairro() != null && !pacienteAtualizado.getBairro().isBlank()) {
            pacientePersistido.setBairro(pacienteAtualizado.getBairro());
        }

        if (pacienteAtualizado.getDataConsulta() != null &&
                pacienteAtualizado.getDataConsulta().isAfter(LocalDateTime.now())) {
            pacientePersistido.setDataConsulta(pacienteAtualizado.getDataConsulta());
        }

        if (pacienteAtualizado.getStatus() != null && !pacienteAtualizado.getStatus().isBlank()) {
            pacientePersistido.setStatus(pacienteAtualizado.getStatus());
        }

        return pacientePersistido;
    }

}
