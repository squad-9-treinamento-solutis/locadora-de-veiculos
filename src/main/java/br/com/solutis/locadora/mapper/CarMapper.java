package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.CarDto;
import br.com.solutis.locadora.model.entity.CarEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper extends GenericMapper<CarEntity, CarDto> {
    @Override
    CarDto modelToDTO(CarEntity entity);

    @Override
    CarEntity dtoToModel(CarDto dto);

    @Override
    List<CarDto> listModelToListDto(List<CarEntity> all);
}