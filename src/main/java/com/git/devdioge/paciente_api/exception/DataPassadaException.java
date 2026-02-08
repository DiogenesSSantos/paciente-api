package com.git.devdioge.paciente_api.exception;


/**
 * @author Diogenes Santos.
 * Exception para uso da regra de negócio, aonde a data de
 */
public class DataPassadaException extends RuntimeException {

    public DataPassadaException(String message) {
        super(message);
    }
}
