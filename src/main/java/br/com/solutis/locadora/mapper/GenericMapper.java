package br.com.solutis.locadora.mapper;


import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface GenericMapper<E,D> {
    @Mapping(target = ".", source = ".", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    D modelToDTO(E entity);

    @Mapping(target = ".", source = ".", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E dtoToModel(D dto);
}
