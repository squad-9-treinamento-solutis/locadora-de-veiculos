package br.com.solutis.locadora.exception.rent;

public class RentAlreadyConfirmedException extends RuntimeException {
    public RentAlreadyConfirmedException(Long id) {
        super("Rent with ID " + id + " is already confirmed.");
    }
}