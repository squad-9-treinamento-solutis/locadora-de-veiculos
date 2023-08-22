package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.accessory.AccessoryException;
import br.com.solutis.locadora.exception.car.accessory.AccessoryNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.AccessoryDto;
import br.com.solutis.locadora.model.entity.car.Accessory;
import br.com.solutis.locadora.repository.car.AccessoryRepository;
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
public class AccessoryService implements CrudService<AccessoryDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final AccessoryRepository accessoryRepository;
    private final GenericMapper<AccessoryDto, Accessory> modelMapper;

    public AccessoryDto findById(Long id) {
        LOGGER.info("Finding accessory with ID: {}", id);
        Accessory accessory = getAccessory(id);

        return modelMapper.mapModelToDto(accessory, AccessoryDto.class);
    }

    public PageResponse<AccessoryDto> findAll(int pageNo, int pageSize) throws AccessoryNotFoundException{

            LOGGER.info("Fetching accessories with page number {} and page size {}.", pageNo, pageSize);
            try{
            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Accessory> pagedAccessories = accessoryRepository.findAll(paging);
            List<AccessoryDto> accessoryDtos = modelMapper.
                    mapList(pagedAccessories.getContent(), AccessoryDto.class);

            PageResponse<AccessoryDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(accessoryDtos);
            pageResponse.setCurrentPage(pagedAccessories.getNumber());
            pageResponse.setTotalItems(pagedAccessories.getTotalElements());
            pageResponse.setTotalPages(pagedAccessories.getTotalPages());

            return pageResponse;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                throw new AccessoryException("An error occurred while finding accessory.", e);
            }
    }

    public AccessoryDto add(AccessoryDto payload) throws AccessoryException{

        try{
            LOGGER.info("Adding accessory {}.", payload);

            Accessory accessory = accessoryRepository
                    .save(modelMapper.mapDtoToModel(payload, Accessory.class));

            return modelMapper.mapModelToDto(accessory, AccessoryDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AccessoryException("An error occurred while add accessory.", e);
        }
    }

    public AccessoryDto update(AccessoryDto payload) throws AccessoryException{
        Accessory existingAccessory = getAccessory(payload.getId());
        if (existingAccessory.isDeleted()) throw new AccessoryNotFoundException(existingAccessory.getId());

        try{


            LOGGER.info("Updating accessory {}.", payload);
            AccessoryDto accessoryDto = modelMapper
                    .mapModelToDto(existingAccessory, AccessoryDto.class);

            updateAccessoryFields(payload, accessoryDto);

            Accessory accessory = accessoryRepository
                    .save(modelMapper.mapDtoToModel(accessoryDto, Accessory.class));

            return modelMapper.mapModelToDto(accessory, AccessoryDto.class);

        } catch (Exception e) {
        LOGGER.error(e.getMessage());
        throw new AccessoryException("An error occurred while updating accessory.", e);
        }
    }

    public void deleteById(Long id) {
        AccessoryDto accessoryDto = findById(id);

        try {
            LOGGER.info("Soft deleting accessory with ID {}.", id);

            Accessory accessory = modelMapper.mapDtoToModel(accessoryDto, Accessory.class);
            accessory.setDeleted(true);

            accessoryRepository.save(accessory);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new AccessoryException("An error occurred while deleting accessory.", e);
        }
    }

    public void updateAccessoryFields(AccessoryDto payload, AccessoryDto existingAccessory) {
        if (payload.getDescription() != null) {
            existingAccessory.setDescription(payload.getDescription());
        }
    }

    private Accessory getAccessory(Long id) {
        return accessoryRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Accessory with ID {} not found.", id);
            return new AccessoryNotFoundException(id);
        });
    }
}
