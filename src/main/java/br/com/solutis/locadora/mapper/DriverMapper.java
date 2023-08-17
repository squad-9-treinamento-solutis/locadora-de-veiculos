package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DriverMapper extends GenericMapper<Driver, DriverDto>{
    @Override
    DriverDto modelToDTO(Driver entity);

    @Override
    Driver dtoToModel(DriverDto dto);
}
