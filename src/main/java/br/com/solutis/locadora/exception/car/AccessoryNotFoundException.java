package br.com.solutis.locadora.exception.car;

public class AccessoryNotFoundException extends RuntimeException {
    public AccessoryNotFoundException(Long id) {
        super("Accessory with ID " + id + " not found.");
    }
}

