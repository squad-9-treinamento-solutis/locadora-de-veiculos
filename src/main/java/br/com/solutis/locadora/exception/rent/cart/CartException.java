package br.com.solutis.locadora.exception.rent.cart;

public class CartException extends RuntimeException {
    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}