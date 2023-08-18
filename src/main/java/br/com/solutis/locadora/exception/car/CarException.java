package br.com.solutis.locadora.exception.car;

public class CarException extends RuntimeException {
    public CarException(String message, Throwable cause) {
        super(message, cause);
    }
}
