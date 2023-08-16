package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.DriverEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DriverMapper extends GenericMapper<DriverEntity, DriverDto>{

    @Override
    DriverDto modelToDTO(DriverEntity entity);

    @Override
    DriverEntity dtoToModel(DriverDto dto);
}
