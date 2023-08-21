package br.com.solutis.locadora.exception.car;

public class CarNotRentedException extends RuntimeException {
    public CarNotRentedException(Long id) {
        super("Car with ID " + id + " is not rented.");
    }
}
