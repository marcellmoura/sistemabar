package com.bar.sistemabar.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponseRecord> tratarBusinessException(
            BusinessException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseRecord> tratarRecursoNaoEncontradoException(
            RecursoNaoEncontradoException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        exception.getMessage(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseRecord> tratarErroValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        mensagem,
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<ErroResponseRecord> tratarRotaNaoEncontrada(
            Exception exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        "Endpoint não encontrado.",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResponseRecord> tratarMetodoNaoPermitido(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        "Método HTTP não permitido para este endpoint.",
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseRecord> tratarErroInterno(
            Exception exception,
            HttpServletRequest request
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(criarErroResponse(
                        status,
                        "Erro interno inesperado.",
                        request.getRequestURI()
                ));
    }

    private ErroResponseRecord criarErroResponse(
            HttpStatus status,
            String mensagem,
            String path
    ) {

        return new ErroResponseRecord(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                path
        );
    }
}