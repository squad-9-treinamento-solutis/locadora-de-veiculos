package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.mapper.rent.RentMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.rent.Rent;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RentService implements CrudService<RentDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Rent> rentRepository;
    private final RentMapper rentMapper;

    public RentDto findById(Long id) {
        LOGGER.info("Finding rent with ID: {}", id);

        return rentRepository.findById(id)
                .map(rentMapper::modelToDTO)
                .orElseThrow(() -> {
                    LOGGER.error("Rent with ID {} not found.", id);
                    return new RentNotFoundException(id);
                });
    }

    public PageResponse<RentDto> findAll(int pageNo, int pageSize) {
        return null;
    }


    public RentDto add(RentDto payload) {
        return null;
    }

    public RentDto update(RentDto payload) {
        return null;
    }


    public void deleteById(Long id) {
        RentDto rentDto = findById(id);

        try {
            LOGGER.info("Soft deleting rent with ID: {}", id);

            Rent rent = rentMapper.dtoToModel(rentDto);
            rent.setDeleted(true);

            rentRepository.save(rent);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RentException("An error occurred while deleting car.", e);
        }
    }
}
