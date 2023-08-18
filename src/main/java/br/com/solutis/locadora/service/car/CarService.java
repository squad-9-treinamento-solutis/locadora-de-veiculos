package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.mapper.car.CarMapper;
import br.com.solutis.locadora.model.dto.car.CarDto;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.repository.CarRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
import lombok.RequiredArgsConstructor;
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
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public PageResponse<CarDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Car> carsPage = carRepository.findByRented(false, paging);
        List<Car> cars = carsPage.getContent();
        List<CarDto> dtoCars = carMapper.listModelToListDto(cars);

        PageResponse<CarDto> response = new PageResponse<>();
        response.setContent(dtoCars);
        response.setCurrentPage(carsPage.getNumber());
        response.setTotalItems(carsPage.getTotalElements());
        response.setTotalPages(carsPage.getTotalPages());

        return response;
    }

    @Override
    public CarDto findById(Long id) {
        return carRepository.findById(id).map(carMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("Car Not found"));
    }

    @Override
    public CarDto add(CarDto payload) {
        Car car = carRepository
                .save(carMapper.dtoToModel(payload));

        return carMapper.modelToDTO(car);
    }

    @Override
    public CarDto update(CarDto payload) {
        return carRepository.findById(payload.getId()).map(car -> {
            carRepository.save(carMapper.dtoToModel(payload));
            return carMapper.modelToDTO(car);
        }).orElseThrow(() -> new BadRequestException("Car Not found"));
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
