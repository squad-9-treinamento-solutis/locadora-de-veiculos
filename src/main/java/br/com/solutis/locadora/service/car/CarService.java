package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.mapper.car.CarMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.repository.CarRepository;
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
public class CarService implements CrudService<CarDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarDto findById(Long id) {
        LOGGER.info("Finding car with ID: {}", id);

        return carRepository.findById(id)
                .map(carMapper::modelToDTO)
                .orElseThrow(() -> {
                    LOGGER.error("Car with ID {} not found.", id);
                    return new CarNotFoundException(id);
                });
    }


    public PageResponse<CarDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching cars with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Car> pagedCars = carRepository.findByRented(false, paging);
            List<CarDto> carDtos = carMapper.listModelToListDto(pagedCars.getContent());

            PageResponse<CarDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(carDtos);
            pageResponse.setCurrentPage(pagedCars.getNumber());
            pageResponse.setTotalItems(pagedCars.getTotalElements());
            pageResponse.setTotalPages(pagedCars.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while fetching cars.", e);
        }
    }

    public CarDto add(CarDto payload) {
        try {
            LOGGER.info("Adding car: {}", payload);

            Car car = carRepository
                    .save(carMapper.dtoToModel(payload));

            return carMapper.modelToDTO(car);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while adding car.", e);
        }
    }

    public CarDto update(CarDto payload) {
        findById(payload.getId());

        try {
            LOGGER.info("Updating car: {}", payload);

            Car car = carRepository
                    .save(carMapper.dtoToModel(payload));

            return carMapper.modelToDTO(car);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while updating car.", e);
        }
    }

    public void deleteById(Long id) {
        CarDto carDto = findById(id);

        try {
            LOGGER.info("Soft deleting car with ID: {}", id);

            Car car = carMapper.dtoToModel(carDto);
            car.setDeleted(true);

            carRepository.save(car);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while deleting car.", e);
        }
    }
}
