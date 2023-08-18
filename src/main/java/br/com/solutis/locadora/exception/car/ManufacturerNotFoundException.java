package br.com.solutis.locadora.exception.car;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException(Long id) {
        super("Manufacturer with ID " + id + " not found.");
    }
}

