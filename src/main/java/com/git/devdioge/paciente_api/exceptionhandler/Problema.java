package com.git.devdioge.paciente_api.exceptionhandler;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Problema(
        int statusCode,
        String mensagem,
        String mensagemUsuario,
        String classeException,
        LocalDateTime timeStamp,

        List<ErrorCampos> errorsCampos
) {}


