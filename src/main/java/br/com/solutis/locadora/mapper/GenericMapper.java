package br.com.solutis.locadora.mapper;


import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface GenericMapper<E, D> {
    @Mapping(target = ".", source = ".", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    D modelToDTO(E entity);

    @Mapping(target = ".", source = ".", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E dtoToModel(D dto);

    @Mapping(target = ".", source = ".", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<D> listModelToListDto(List<E> all);
}
