package br.com.solutis.locadora.exception.car;

public class CarAlreadyRentedException extends RuntimeException {
    public CarAlreadyRentedException(Long id) {
        super("Car with ID " + id + " is already rented.");
    }
}

