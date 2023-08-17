package br.com.solutis.locadora.mapper.car;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.ManufacturerDto;
import br.com.solutis.locadora.model.entity.car.Manufacturer;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ManufacturerMapper extends GenericMapper<Manufacturer, ManufacturerDto> {
    @Override
    ManufacturerDto modelToDTO(Manufacturer entity);

    @Override
    Manufacturer dtoToModel(ManufacturerDto dto);
}
