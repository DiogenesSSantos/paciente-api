package com.git.devdioge.paciente_api.exception;

public class PacienteNaoLocalizadoException extends RuntimeException{

    public PacienteNaoLocalizadoException(String message) {
        super(message);
    }
}
