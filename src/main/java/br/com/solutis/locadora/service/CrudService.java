package br.com.solutis.locadora.service;

import java.util.List;

public interface CrudService<T> {
    T findById(Long id);

    List<T> findAll(int pageNo, int pageSize);

    T add(T payload);

    T update(T payload);

    void deleteById(Long id);
}
