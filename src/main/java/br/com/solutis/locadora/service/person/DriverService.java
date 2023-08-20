package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.exception.person.DriverException;
import br.com.solutis.locadora.exception.person.DriverNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.person.DriverDto;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.repository.person.DriverRepository;
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
public class DriverService implements CrudService<DriverDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;
    private final GenericMapper<DriverDto, Driver> modelMapper;

    public DriverDto findById(Long id) {
        LOGGER.info("Finding driver with ID: {}", id);
        Driver driver = getDriver(id);

        return modelMapper.mapModelToDto(driver, DriverDto.class);
    }

    public PageResponse<DriverDto> findAll(int pageNo, int pageSize) {
        LOGGER.info("Fetching drivers with page number {} and page size {}.", pageNo, pageSize);

        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Driver> pagedDrivers = driverRepository.findByDeletedFalse(paging);

            List<DriverDto> driverDtos = modelMapper
                    .mapList(pagedDrivers.getContent(), DriverDto.class);

            PageResponse<DriverDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(driverDtos);
            pageResponse.setCurrentPage(pagedDrivers.getNumber());
            pageResponse.setTotalItems(pagedDrivers.getTotalElements());
            pageResponse.setTotalPages(pagedDrivers.getTotalPages());

            return pageResponse;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DriverException("An error occurred while fetching drivers.", e);
        }
    }

    public DriverDto add(DriverDto payload) {
        try {
            LOGGER.info("Adding driver: {}", payload);

            Driver driver = driverRepository
                    .save(modelMapper.mapDtoToModel(payload, Driver.class));

            return modelMapper.mapModelToDto(driver, DriverDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DriverException("An error occurred while adding driver.", e);
        }
    }

    public DriverDto update(DriverDto payload) {
        Driver existingDriver = getDriver(payload.getId());
        if (existingDriver.isDeleted()) throw new DriverNotFoundException(existingDriver.getId());

        try {
            LOGGER.info("Updating driver: {}", payload);
            DriverDto driverDto = modelMapper
                    .mapModelToDto(existingDriver, DriverDto.class);

            updateDriverFields(payload, driverDto);

            Driver driver = driverRepository
                    .save(modelMapper.mapDtoToModel(driverDto, Driver.class));

            return modelMapper.mapModelToDto(driver, DriverDto.class);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DriverException("An error occurred while updating driver.", e);
        }
    }

    public void deleteById(Long id) {
        DriverDto driverDto = findById(id);

        try {
            LOGGER.info("Soft deleting driver with ID: {}", id);


            Driver driver = modelMapper.mapDtoToModel(driverDto, Driver.class);
            driver.setDeleted(true);

            driverRepository.save(driver);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new DriverException("An error occurred while deleting driver.", e);
        }
    }

    private void updateDriverFields(DriverDto payload, DriverDto existingDriver) {
        if (payload.getName() != null) {
            existingDriver.setName(payload.getName());
        }
        if (payload.getCnh() != null) {
            existingDriver.setCnh(payload.getCnh());
        }
        if (payload.getBirthDate() != null) {
            existingDriver.setBirthDate(payload.getBirthDate());
        }
        if (payload.getGender() != null) {
            existingDriver.setGender(payload.getGender());
        }
    }

    private Driver getDriver(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Driver with ID {} not found.", id);
            return new DriverNotFoundException(id);
        });
    }
}
