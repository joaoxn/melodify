package com.melodify.infra.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatusCode;

@Getter
public class ExceptionResponse {
    private final int status;
    private final HttpStatusCode erro;
    private final String mensagem;

    public ExceptionResponse(@NonNull HttpStatusCode error) {
        this.status = error.value();
        this.erro = error;
        this.mensagem = "Um erro inesperado ocorreu";
    }

    public ExceptionResponse(@NonNull HttpStatusCode error, @NonNull String message) {
        this.status = error.value();
        this.erro = error;
        this.mensagem = message;
    }
}