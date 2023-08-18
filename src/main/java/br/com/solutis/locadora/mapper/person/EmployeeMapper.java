package br.com.solutis.locadora.mapper.person;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.person.EmployeeDto;
import br.com.solutis.locadora.model.entity.person.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EmployeeMapper extends GenericMapper<Employee, EmployeeDto> {
    @Override
    EmployeeDto modelToDTO(Employee entity);

    @Override
    Employee dtoToModel(EmployeeDto dto);
}
