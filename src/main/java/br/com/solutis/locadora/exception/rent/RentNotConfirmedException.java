package br.com.solutis.locadora.exception.rent;

public class RentNotConfirmedException extends RuntimeException {
    public RentNotConfirmedException(Long id) {
        super("Rent with ID " + id + " not confirmed.");
    }
}
