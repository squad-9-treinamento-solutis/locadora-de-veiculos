package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.mapper.person.DriverMapper;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.service.CrudService;
import br.com.solutis.locadora.service.rent.InsurancePolicyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<DriverDto> findAll(int pageNo, int pageSize) {
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
        try {
            LOGGER.info("Deleting driver with ID: {}", id);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while deleting driver.", e);
        }
    }
}
