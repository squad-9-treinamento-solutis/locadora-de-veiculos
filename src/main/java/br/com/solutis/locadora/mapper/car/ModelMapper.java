package br.com.solutis.locadora.mapper.car;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.model.entity.car.Model;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ModelMapper extends GenericMapper<Model, ModelDto> {
    @Override
    ModelDto modelToDTO(Model entity);

    @Override
    Model dtoToModel(ModelDto dto);
}
