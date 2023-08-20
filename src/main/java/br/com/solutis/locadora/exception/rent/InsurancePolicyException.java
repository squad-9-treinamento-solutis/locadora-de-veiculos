package br.com.solutis.locadora.exception.rent;

public class InsurancePolicyException extends RuntimeException {
    public InsurancePolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}