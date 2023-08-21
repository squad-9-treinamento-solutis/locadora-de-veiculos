package br.com.solutis.locadora.exception.person.employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee with ID " + id + " not found.");
    }
}
