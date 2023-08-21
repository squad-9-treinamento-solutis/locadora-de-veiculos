package br.com.solutis.locadora.exception.person.driver;

public class DriverNotAuthorizedException extends RuntimeException {
    public DriverNotAuthorizedException(Long id) {
        super("Driver with ID " + id + " is not authorized to drive this vehicle.");
    }
}
