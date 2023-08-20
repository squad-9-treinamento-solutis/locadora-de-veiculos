package br.com.solutis.locadora.exception.cart;

public class CartException extends RuntimeException {
    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}