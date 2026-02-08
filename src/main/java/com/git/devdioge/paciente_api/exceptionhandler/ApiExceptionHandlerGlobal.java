package com.git.devdioge.paciente_api.exceptionhandler;


import com.git.devdioge.paciente_api.exception.PacienteNaoLocalizadoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandlerGlobal {


    @ExceptionHandler(PacienteNaoLocalizadoException.class)
    public ResponseEntity<Problema>  PacienteNaoLocalizadoException(PacienteNaoLocalizadoException ex ,
                                                             WebRequest webRequest,
                                                             HttpServletRequest httpServletRequest){
        var problema = new Problema(
                    HttpStatus.NOT_FOUND.value(),
                    ex.getMessage(),
                    "O id passado não existe no banco de dados, passe um id válido.",
                PacienteNaoLocalizadoException.class.getSimpleName(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problema> IllegalArgumentException(IllegalArgumentException ex ,
                                                             WebRequest webRequest,
                                                             HttpServletRequest request) {

        var problema = new Problema(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "Campo obrigatório está inválido.",
                IllegalArgumentException.class.getSimpleName(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);


    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problema> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ErrorCampos> errorCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ErrorCampos(fe.getField(), fe.getDefaultMessage()))
                .toList();

        var problema = new Problema(
                HttpStatus.BAD_REQUEST.value(),
                "Campos invalidos.",
                "Um ou mais campos estão invalidos.",
                MethodArgumentNotValidException.class.getSimpleName(),
                LocalDateTime.now(),
                errorCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);

    }


}
