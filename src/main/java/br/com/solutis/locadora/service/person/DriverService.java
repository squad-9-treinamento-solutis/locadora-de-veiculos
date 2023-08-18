package br.com.solutis.locadora.service.person;

import br.com.solutis.locadora.exception.car.CarNotFoundException;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Driver> driverRepository;
    private final DriverMapper driverMapper;

    public DriverDto findById(Long id) {
        LOGGER.info("Finding driver with ID: {}", id);
        return driverRepository.findById(id).map(driverMapper::modelToDTO).orElseThrow(()-> {
            LOGGER.error("Driver with ID {} not found.", id);
            return new CarNotFoundException(id);
        });

    }

    @Override
    public PageResponse<DriverDto> findAll(int pageNo, int pageSize) {
        LOGGER.info("Fetching drivers with page number {} and page size {}.", pageNo, pageSize);

        try {
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Driver> pagedDrivers = driverRepository.findAll(paging);

            List<DriverDto> driverDtos = driverMapper.listModelToListDto(pagedDrivers.getContent());

            PageResponse<DriverDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(driverDtos);
            pageResponse.setCurrentPage(pagedDrivers.getNumber());
            pageResponse.setTotalItems(pagedDrivers.getTotalElements());
            pageResponse.setTotalPages(pagedDrivers.getTotalPages());

            return pageResponse;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while fetching drivers.", e);
        }
    }

    public DriverDto add(DriverDto payload) {
        try {
            LOGGER.info("Adding driver: {}", payload);

            Driver driver = driverRepository.save(driverMapper.dtoToModel(payload));
            return driverMapper.modelToDTO(driver);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("An error occurred while adding driver.", e);
        }
    }

    public DriverDto update(DriverDto payload) {
        DriverDto existingDriver = findById(payload.getId());

        try {
            LOGGER.info("Updating driver: {}", payload);

            updateDriverFields(payload, existingDriver);

            Driver driver = driverRepository.save(driverMapper.dtoToModel(existingDriver));

            return driverMapper.modelToDTO(driver);

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
    private void updateDriverFields(DriverDto payload, DriverDto existingDriver) {

        if(payload.getName()!= null){
            existingDriver.setName(payload.getName());
        }
        if(payload.getCnh()!= null){
            existingDriver.setCnh(payload.getCnh());
        }
        if(payload.getBirthDate()!= null){
            existingDriver.setBirthDate(payload.getBirthDate());
        }
        if(payload.getGenderEnum()!= null){
            existingDriver.setGenderEnum(payload.getGenderEnum());
        }
    }
}
