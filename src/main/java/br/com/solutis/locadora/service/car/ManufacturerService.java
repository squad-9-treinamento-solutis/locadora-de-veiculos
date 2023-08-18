package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.ManufacturerException;
import br.com.solutis.locadora.exception.car.ManufacturerNotFoundException;
import br.com.solutis.locadora.mapper.car.ManufacturerMapper;
import br.com.solutis.locadora.model.dto.car.ManufacturerDto;
import br.com.solutis.locadora.model.entity.car.Manufacturer;
import br.com.solutis.locadora.repository.CrudRepository;
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
public class ManufacturerService implements CrudService<ManufacturerDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Manufacturer> manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    public ManufacturerDto findById(Long id) {
        return manufacturerRepository.findById(id)
                .map(manufacturerMapper::modelToDTO)
                .orElseThrow(() -> {
                    LOGGER.error("Manufacturer with ID {} not found.", id);
                    return new ManufacturerNotFoundException(id);
                });
    }

    public List<ManufacturerDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching manufacturers with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Manufacturer> manufacturers = manufacturerRepository.findAll(paging);

            return manufacturerMapper.listModelToListDto(manufacturers);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while fetching manufacturers.", e);
        }
    }

    public ManufacturerDto add(ManufacturerDto payload) {
        try {
            LOGGER.info("Adding manufacturer: {}", payload);

            Manufacturer manufacturer = manufacturerRepository
                    .save(manufacturerMapper.dtoToModel(payload));
            return manufacturerMapper.modelToDTO(manufacturer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while adding manufacturer.", e);
        }
    }

    public ManufacturerDto update(ManufacturerDto payload) {
        findById(payload.getId());

        try {
            LOGGER.info("Updating manufacturer: {}", payload);

            Manufacturer manufacturer = manufacturerRepository
                    .save(manufacturerMapper.dtoToModel(payload));

            return manufacturerMapper.modelToDTO(manufacturer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while updating manufacturer.", e);
        }
    }

    public void deleteById(Long id) {
        ManufacturerDto manufacturerDto = findById(id);

        try {
            LOGGER.info("Soft deleting manufacturer with ID: {}", id);

            Manufacturer manufacturer = manufacturerMapper.dtoToModel(manufacturerDto);
            manufacturer.setDeleted(true);

            manufacturerRepository.save(manufacturer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while deleting manufacturer.", e);
        }
    }
}