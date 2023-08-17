package br.com.solutis.locadora.service;

import java.util.List;

public interface CrudService<T extends AbstractDto> {
    T findById(Long id);

    List<T> findAll();

    T add(T payload);

    T update(T payload);

    void deleteById(Long id);
}
