package com.prova.insper.entregas.erros;
public class DadosInvalidosException extends RuntimeException {

    public DadosInvalidosException(String message) {
        super(message);
    }
}