package br.com.solutis.locadora.exception.car.model;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id) {
        super("Model with ID " + id + " not found.");
    }
}

