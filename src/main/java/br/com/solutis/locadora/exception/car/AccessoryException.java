package br.com.solutis.locadora.exception.car;

public class AccessoryException extends RuntimeException {
    public AccessoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
