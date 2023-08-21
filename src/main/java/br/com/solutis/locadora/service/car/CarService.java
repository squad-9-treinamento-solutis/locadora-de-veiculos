package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.exception.car.ManufacturerNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.entity.car.Accessory;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import br.com.solutis.locadora.repository.car.CarRepository;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class CarService implements CrudService<CarDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;
    private final GenericMapper<CarDto, Car> modelMapper;

    public CarDto findById(Long id) {
        LOGGER.info("Finding car with ID: {}", id);
        Car car = getCar(id);

        return modelMapper.mapModelToDto(car, CarDto.class);
    }

    public PageResponse<CarDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching cars with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Car> pagedCars = carRepository.findByDeletedFalseAndRentedFalse(paging);
            List<CarDto> carDtos = modelMapper.
                    mapList(pagedCars.getContent(), CarDto.class);

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
                    .save(modelMapper.mapDtoToModel(payload, Car.class));

            return modelMapper.mapModelToDto(car, CarDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while adding car.", e);
        }
    }

    public CarDto update(CarDto payload) {
        Car existingCar = getCar(payload.getId());
        if (existingCar.isDeleted()) throw new ManufacturerNotFoundException(existingCar.getId());

        try {
            LOGGER.info("Updating car: {}", payload);
            CarDto carDto = modelMapper
                    .mapModelToDto(existingCar, CarDto.class);

            updateModelFields(payload, carDto);

            Car car = carRepository
                    .save(modelMapper.mapDtoToModel(carDto, Car.class));

            return modelMapper.mapModelToDto(car, CarDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while updating car.", e);
        }
    }

    public void deleteById(Long id) {
        CarDto carDto = findById(id);

        try {
            LOGGER.info("Soft deleting car with ID: {}", id);

            Car car = modelMapper.mapDtoToModel(carDto, Car.class);
            car.setDeleted(true);

            carRepository.save(car);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new CarException("An error occurred while deleting car.", e);
        }
    }

    private void updateModelFields(CarDto payload, CarDto existingCar) {
        if (payload.getPlate() != null) {
            existingCar.setPlate(payload.getPlate());
        }
        if (payload.getChassis() != null) {
            existingCar.setChassis(payload.getChassis());
        }
        if (payload.getColor() != null) {
            existingCar.setColor(payload.getColor());
        }
        if (payload.getDailyValue() != null) {
            existingCar.setDailyValue(payload.getDailyValue());
        }
        if (payload.getImageUrl() != null) {
            existingCar.setImageUrl(payload.getImageUrl());
        }

    }

    public List<CarDto> findCarsByFilters(
            ModelCategoryEnum category,
            Accessory accessory,
            String model,
            Boolean rented) {
        List<Car> cars = carRepository.findCarsByFilters(category, accessory, model, rented);
        return modelMapper.mapList(cars, CarDto.class);
    }
    private Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Car with ID {} not found.", id);
            return new CarNotFoundException(id);
        });
    }
}
