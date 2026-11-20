package com.setupforge.setupforge_backend.exception;

import com.setupforge.setupforge_backend.exception.produtoException.ProdutoNaoEncontrado;
import com.setupforge.setupforge_backend.exception.setupException.SetupNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //PRODUTOS EXCEPTION ->
    @ExceptionHandler(ProdutoNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> ProdutoNaoEncontrado(ProdutoNaoEncontrado produtoNaoEncontrado, HttpServletRequest httpServletRequest) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                produtoNaoEncontrado.getMessage(),
                httpServletRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    //SETUP EXCEPTION ->

    @ExceptionHandler(SetupNaoEncontrado.class)
    public ResponseEntity<ErrorResponse> SetupNaoEncontrado(
            SetupNaoEncontrado setupNaoEncontrado, HttpServletRequest httpServletRequest
    ){

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                setupNaoEncontrado.getMessage(),
                httpServletRequest.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
