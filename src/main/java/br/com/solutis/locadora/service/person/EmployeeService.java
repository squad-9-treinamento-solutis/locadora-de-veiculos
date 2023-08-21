package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.exception.person.employee.EmployeeException;
import br.com.solutis.locadora.exception.person.employee.EmployeeNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.person.EmployeeDto;
import br.com.solutis.locadora.model.entity.person.Employee;
import br.com.solutis.locadora.repository.person.EmployeeRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final GenericMapper<EmployeeDto, Employee> modelMapper;

    public EmployeeDto findById(Long id) {
        LOGGER.info("Finding Employee with ID: {}", id);
        Employee employee = getEmployee(id);

        return modelMapper.mapModelToDto(employee, EmployeeDto.class);
    }

    public PageResponse<EmployeeDto> findAll(int pageNo, int pageSize) {
        LOGGER.info("Fetching Employees with page number {} and page size {}.", pageNo, pageSize);

        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Employee> pagedEmployees = employeeRepository.findByDeletedFalse(paging);

            List<EmployeeDto> employeeDtos = modelMapper
                    .mapList(pagedEmployees.getContent(), EmployeeDto.class);

            PageResponse<EmployeeDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(employeeDtos);
            pageResponse.setCurrentPage(pagedEmployees.getNumber());
            pageResponse.setTotalItems(pagedEmployees.getTotalElements());
            pageResponse.setTotalPages(pagedEmployees.getTotalPages());

            return pageResponse;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new EmployeeException("An error occurred while fetching Employees.", e);
        }
    }

    public EmployeeDto add(EmployeeDto payload) {
        try {
            LOGGER.info("Adding Employee: {}", payload);

            Employee Employee = employeeRepository
                    .save(modelMapper.mapDtoToModel(payload, Employee.class));

            return modelMapper.mapModelToDto(Employee, EmployeeDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new EmployeeException("An error occurred while adding Employee.", e);
        }
    }

    public EmployeeDto update(EmployeeDto payload) {
        Employee existingEmployee = getEmployee(payload.getId());
        if (existingEmployee.isDeleted()) throw new EmployeeNotFoundException(existingEmployee.getId());

        try {
            LOGGER.info("Updating Employee: {}", payload);
            EmployeeDto EmployeeDto = modelMapper
                    .mapModelToDto(existingEmployee, EmployeeDto.class);

            updateEmployeeFields(payload, EmployeeDto);

            Employee Employee = employeeRepository
                    .save(modelMapper.mapDtoToModel(EmployeeDto, Employee.class));

            return modelMapper.mapModelToDto(Employee, EmployeeDto.class);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new EmployeeException("An error occurred while updating Employee.", e);
        }
    }

    public void deleteById(Long id) {
        EmployeeDto EmployeeDto = findById(id);

        try {
            LOGGER.info("Soft deleting Employee with ID: {}", id);

            Employee Employee = modelMapper.mapDtoToModel(EmployeeDto, Employee.class);
            Employee.setDeleted(true);

            employeeRepository.save(Employee);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new EmployeeException("An error occurred while deleting Employee.", e);
        }
    }

    private void updateEmployeeFields(EmployeeDto payload, EmployeeDto existingEmployee) {
        if (payload.getName() != null) {
            existingEmployee.setName(payload.getName());
        }
        if (payload.getRegistration() != null) {
            existingEmployee.setRegistration(payload.getRegistration());
        }
        if (payload.getBirthDate() != null) {
            existingEmployee.setBirthDate(payload.getBirthDate());
        }
        if (payload.getGender() != null) {
            existingEmployee.setGender(payload.getGender());
        }
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Employee with ID {} not found.", id);
            return new EmployeeNotFoundException(id);
        });
    }
}
