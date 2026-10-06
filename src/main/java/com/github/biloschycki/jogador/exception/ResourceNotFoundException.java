package com.github.biloschycki.jogador.exception;

/**
 * Exceção lançada quando um recurso solicitado (Jogador, Restaurante, etc.)
 * não é encontrado na base de dados.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
