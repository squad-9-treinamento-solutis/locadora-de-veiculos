package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotFoundException;
import br.com.solutis.locadora.exception.car.ManufacturerNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.dto.car.CarDtoResponse;
import br.com.solutis.locadora.model.entity.car.Accessory;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import br.com.solutis.locadora.repository.car.AccessoryRepository;
import br.com.solutis.locadora.repository.car.CarRepository;
import br.com.solutis.locadora.response.PageResponse;
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
public class CarService  {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);
    private final CarRepository carRepository;
    private final AccessoryRepository accessoryRepository;
    private final GenericMapper<CarDto, Car> modelMapper;
    private final GenericMapper<CarDtoResponse, Car> modelMapperResponse;

    public CarDtoResponse findById(Long id) {
        LOGGER.info("Finding car with ID: {}", id);
        Car car = getCar(id);

        return modelMapperResponse.mapModelToDto(car, CarDtoResponse.class);
    }

    public PageResponse<CarDtoResponse> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching cars with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Car> pagedCars = carRepository.findByDeletedFalseAndRentedFalse(paging);
            List<CarDtoResponse> carDtos = modelMapperResponse.mapList(pagedCars.getContent(),CarDtoResponse.class);

            PageResponse<CarDtoResponse> pageResponse = new PageResponse<>();
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

            List<Accessory> accessories = accessoryRepository.findAllById(payload.getAccessoriesIds());

            Car car = modelMapper.mapDtoToModel(payload, Car.class);
            car.setAccessories(accessories);
            car.setRented(false);

            return modelMapper.mapModelToDto(carRepository.save(car), CarDto.class);
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


        try {
            LOGGER.info("Soft deleting car with ID: {}", id);

            Car car = getCar(id);
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

    public List<CarDtoResponse> findCarsByFilters(
            ModelCategoryEnum category,
            Accessory accessory,
            String model,
            Boolean rented) {
        List<Car> cars = carRepository.findCarsByFilters(category, accessory, model, rented);
        return modelMapperResponse.mapList(cars, CarDtoResponse.class);
    }
    private Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Car with ID {} not found.", id);
            return new CarNotFoundException(id);
        });
    }

}
