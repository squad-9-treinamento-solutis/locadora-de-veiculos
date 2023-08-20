package br.com.solutis.locadora.exception.rent;

public class InsurancePolicyNotFoundException extends RuntimeException {
    public InsurancePolicyNotFoundException(Long id) {
        super("Insurance Policy with ID " + id + " not found.");
    }
}