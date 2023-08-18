package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.mapper.person.DriverMapper;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
import br.com.solutis.locadora.service.rent.InsurancePolicyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class DriverService implements CrudService<DriverDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Driver> driverRepository;
    private final DriverMapper driverMapper;

    public DriverDto findById(Long id) {
        LOGGER.info("Finding driver with ID: {}", id);

        return null;
    }

    @Override
    public PageResponse<DriverDto> findAll(int pageNo, int pageSize) {
        LOGGER.info("Fetching drivers with page number {} and page size {}.", pageNo, pageSize);

        try {
            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while fetching drivers.", e);
        }
    }

    public DriverDto add(DriverDto payload) {
        try {
            LOGGER.info("Adding driver: {}", payload);

            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while adding driver.", e);
        }
    }

    public DriverDto update(DriverDto payload) {
        try {
            LOGGER.info("Updating driver: {}", payload);

            return null;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while updating driver.", e);
        }
    }

    public void deleteById(Long id) {
        DriverDto driverDto = findById(id);

        try {
            LOGGER.info("Soft deleting driver with ID: {}", id);

            Driver driver = driverMapper.dtoToModel(driverDto);
            driver.setDeleted(true);

            driverRepository.save(driver);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while deleting driver.", e);
        }
    }
}
