package br.com.solutis.locadora.mapper;

public interface GenericMapper<E,D> {
    D modelToDTO(E entity);

    E dtoToModel(D dto);
}
