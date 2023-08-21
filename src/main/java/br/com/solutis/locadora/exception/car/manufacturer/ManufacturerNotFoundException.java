package br.com.solutis.locadora.exception.car.manufacturer;

public class ManufacturerNotFoundException extends RuntimeException {
    public ManufacturerNotFoundException(Long id) {
        super("Manufacturer with ID " + id + " not found.");
    }
}

