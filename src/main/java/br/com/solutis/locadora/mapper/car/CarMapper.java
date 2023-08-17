package br.com.solutis.locadora.mapper.car;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.entity.car.Car;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CarMapper extends GenericMapper<Car, CarDto> {
    @Override
    CarDto modelToDTO(Car entity);

    @Override
    Car dtoToModel(CarDto dto);

    @Override
    List<CarDto> listModelToListDto(Page<Car> all);
}
