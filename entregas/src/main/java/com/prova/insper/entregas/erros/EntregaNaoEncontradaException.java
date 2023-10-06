package com.prova.insper.entregas.erros;

public class EntregaNaoEncontradaException extends RuntimeException {

    public EntregaNaoEncontradaException(String message) {
        super(message);
    }
}