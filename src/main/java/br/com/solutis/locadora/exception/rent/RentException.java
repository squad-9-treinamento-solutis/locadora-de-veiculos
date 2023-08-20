package br.com.solutis.locadora.exception.rent;

public class RentException extends RuntimeException {
    public RentException(String message, Throwable cause) {
        super(message, cause);
    }
}