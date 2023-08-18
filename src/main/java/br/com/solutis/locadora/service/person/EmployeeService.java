package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.exception.person.EmployeeNotFoundException;
import br.com.solutis.locadora.mapper.person.EmployeeMapper;
import br.com.solutis.locadora.model.dto.person.EmployeeDto;
import br.com.solutis.locadora.model.entity.person.Employee;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
import br.com.solutis.locadora.service.rent.InsurancePolicyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeService implements CrudService<EmployeeDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Employee> employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeDto findById(Long id) {
        LOGGER.info("Finding Employee with ID: {}", id);
        return employeeRepository.findById(id).map(employeeMapper::modelToDTO).orElseThrow(()-> {
            LOGGER.error("Employee with ID {} not found.", id);
            return new EmployeeNotFoundException(id);
        });

    }

    @Override
    public PageResponse<EmployeeDto> findAll(int pageNo, int pageSize) {
        LOGGER.info("Fetching Employees with page number {} and page size {}.", pageNo, pageSize);

        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Employee> pagedEmployees = employeeRepository.findAll(paging);

            List<EmployeeDto> EmployeeDtos = employeeMapper.listModelToListDto(pagedEmployees.getContent());

            PageResponse<EmployeeDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(EmployeeDtos);
            pageResponse.setCurrentPage(pagedEmployees.getNumber());
            pageResponse.setTotalItems(pagedEmployees.getTotalElements());
            pageResponse.setTotalPages(pagedEmployees.getTotalPages());

            return pageResponse;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while fetching Employees.", e);
        }
    }

    public EmployeeDto add(EmployeeDto payload) {
        try {
            LOGGER.info("Adding Employee: {}", payload);

            Employee Employee = employeeRepository.save(employeeMapper.dtoToModel(payload));
            return employeeMapper.modelToDTO(Employee);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while adding Employee.", e);
        }
    }

    public EmployeeDto update(EmployeeDto payload) {
        EmployeeDto existingEmployee = findById(payload.getId());

        try {
            LOGGER.info("Updating Employee: {}", payload);

            updateEmployeeFields(payload, existingEmployee);

            Employee Employee = employeeRepository.save(employeeMapper.dtoToModel(existingEmployee));

            return employeeMapper.modelToDTO(Employee);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while updating Employee.", e);
        }
    }

    public void deleteById(Long id) {
        EmployeeDto EmployeeDto = findById(id);

        try {
            LOGGER.info("Soft deleting Employee with ID: {}", id);


            Employee Employee = employeeMapper.dtoToModel(EmployeeDto);
            Employee.setDeleted(true);

            employeeRepository.save(Employee);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while deleting Employee.", e);
        }
    }
    private void updateEmployeeFields(EmployeeDto payload, EmployeeDto existingEmployee) {

        if(payload.getName()!= null){
            existingEmployee.setName(payload.getName());
        }
        if(payload.getRegistration()!= null){
            existingEmployee.setRegistration(payload.getRegistration());
        }
        if(payload.getBirthDate()!= null){
            existingEmployee.setBirthDate(payload.getBirthDate());
        }
        if(payload.getGenderEnum()!= null){
            existingEmployee.setGenderEnum(payload.getGenderEnum());
        }
    }
}
