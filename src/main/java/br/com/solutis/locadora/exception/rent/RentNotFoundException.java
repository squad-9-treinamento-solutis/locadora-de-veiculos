package br.com.solutis.locadora.exception.rent;

public class RentNotFoundException extends RuntimeException{
    public RentNotFoundException(Long id) {
        super("Rent with ID " + id + " not found.");
    }
}