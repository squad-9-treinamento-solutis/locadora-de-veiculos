package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.DriverEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper extends GenericMapper<DriverEntity, DriverDto> {

    @Override
    DriverDto modelToDTO(DriverEntity entity);

    @Override
    DriverEntity dtoToModel(DriverDto dto);
}