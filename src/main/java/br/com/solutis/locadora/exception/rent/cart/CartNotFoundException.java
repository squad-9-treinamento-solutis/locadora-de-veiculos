package br.com.solutis.locadora.exception.rent.cart;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super("Cart with ID " + id + " not found.");
    }
}