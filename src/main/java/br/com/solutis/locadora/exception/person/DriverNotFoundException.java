package br.com.solutis.locadora.exception.person;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(Long id) {
        super("Driver with ID " + id + " not found.");
    }
}
