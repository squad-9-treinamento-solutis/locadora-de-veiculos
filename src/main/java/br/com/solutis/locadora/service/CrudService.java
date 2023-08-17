package br.com.solutis.locadora.service;

import br.com.solutis.locadora.model.dto.AbstractDto;

import java.util.List;

public interface CrudService<T> {
    T findById(Long id);

    List<T> findAll();

    T add(T payload);

    T update(T payload);

    void deleteById(Long id);
}
