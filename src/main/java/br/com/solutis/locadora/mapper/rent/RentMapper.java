package br.com.solutis.locadora.mapper.rent;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.model.entity.rent.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RentMapper extends GenericMapper<Rent, RentDto> {
    @Override
    RentDto modelToDTO(Rent entity);

    @Override
    Rent dtoToModel(RentDto dto);

    @Override
    List<RentDto> listModelToListDto(List<Rent> all);
}
