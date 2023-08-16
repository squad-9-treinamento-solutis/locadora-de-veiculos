package br.com.solutis.locadora.service;

import br.com.solutis.locadora.model.dto.AbstractDto;
import br.com.solutis.locadora.model.entity.AbstractEntity;

import java.util.List;


public abstract class AbstractService<T extends AbstractDto> {
    public abstract T findById(Long id);

    public abstract List<T> findAll();

    public abstract T add(T payload);

    public abstract T update(T payload);

    public abstract void deleteById(Long id);
}
