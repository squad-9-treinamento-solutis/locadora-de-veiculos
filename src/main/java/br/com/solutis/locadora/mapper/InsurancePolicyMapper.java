package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface InsurancePolicyMapper extends GenericMapper<InsurancePolicyEntity, InsurancePolicyDto>{

    @Override
    InsurancePolicyDto modelToDTO(InsurancePolicyEntity entity);

    @Override
    InsurancePolicyEntity dtoToModel(InsurancePolicyDto dto);

    @Override
    List<InsurancePolicyDto> listModelToListDto(List<InsurancePolicyEntity> all);
}
