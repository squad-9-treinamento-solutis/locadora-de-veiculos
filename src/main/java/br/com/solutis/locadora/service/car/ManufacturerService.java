package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.manufacturer.ManufacturerException;
import br.com.solutis.locadora.exception.car.manufacturer.ManufacturerNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.ManufacturerDto;
import br.com.solutis.locadora.model.entity.car.Manufacturer;
import br.com.solutis.locadora.repository.car.ManufacturerRepository;
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
public class ManufacturerService implements CrudService<ManufacturerDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerService.class);
    private final ManufacturerRepository manufacturerRepository;
    private final GenericMapper<ManufacturerDto, Manufacturer> modelMapper;

    public ManufacturerDto findById(Long id) {
        LOGGER.info("Finding manufacturer with ID: {}", id);
        Manufacturer manufacturer = getManufacturer(id);

        return modelMapper.mapModelToDto(manufacturer, ManufacturerDto.class);
    }

    public PageResponse<ManufacturerDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching manufacturer with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Manufacturer> pagedManufacturers = manufacturerRepository.findByDeletedFalse(paging);

            List<ManufacturerDto> manufacturerDtos = modelMapper
                    .mapList(pagedManufacturers.getContent(), ManufacturerDto.class);

            PageResponse<ManufacturerDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(manufacturerDtos);
            pageResponse.setCurrentPage(pagedManufacturers.getNumber());
            pageResponse.setTotalItems(pagedManufacturers.getTotalElements());
            pageResponse.setTotalPages(pagedManufacturers.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while fetching manufacturers.", e);
        }
    }

    public ManufacturerDto add(ManufacturerDto payload) {
        try {
            LOGGER.info("Adding manufacturer: {}", payload);

            Manufacturer manufacturer = manufacturerRepository
                    .save(modelMapper.mapDtoToModel(payload, Manufacturer.class));

            return modelMapper.mapModelToDto(manufacturer, ManufacturerDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while adding manufacturer.", e);
        }
    }

    public ManufacturerDto update(ManufacturerDto payload) {
        Manufacturer existingManufacturer = getManufacturer(payload.getId());
        if (existingManufacturer.isDeleted()) throw new ManufacturerNotFoundException(existingManufacturer.getId());

        try {
            LOGGER.info("Updating manufacturer: {}", payload);
            ManufacturerDto manufacturerDto = modelMapper
                    .mapModelToDto(existingManufacturer, ManufacturerDto.class);

            updateModelFields(payload, manufacturerDto);

            Manufacturer manufacturer = manufacturerRepository
                    .save(modelMapper.mapDtoToModel(manufacturerDto, Manufacturer.class));

            return modelMapper.mapModelToDto(manufacturer, ManufacturerDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while updating manufacturer.", e);
        }
    }

    public void deleteById(Long id) {
        ManufacturerDto manufacturerDto = findById(id);

        try {
            LOGGER.info("Soft deleting manufacturer with ID: {}", id);

            Manufacturer manufacturer = modelMapper.mapDtoToModel(manufacturerDto, Manufacturer.class);
            manufacturer.setDeleted(true);

            manufacturerRepository.save(manufacturer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ManufacturerException("An error occurred while deleting manufacturer.", e);
        }
    }

    private void updateModelFields(ManufacturerDto payload, ManufacturerDto existingManufacturer) {
        if (payload.getName() != null) {
            existingManufacturer.setName(payload.getName());
        }
    }

    private Manufacturer getManufacturer(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Manufacturer with ID {} not found.", id);
                    return new ManufacturerNotFoundException(id);
                });
    }
}