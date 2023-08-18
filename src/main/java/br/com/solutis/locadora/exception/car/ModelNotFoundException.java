package br.com.solutis.locadora.exception.car;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id) {
        super("Model with ID " + id + " not found.");
    }
}

