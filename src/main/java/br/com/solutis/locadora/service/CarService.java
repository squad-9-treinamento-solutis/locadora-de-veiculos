package br.com.solutis.locadora.service;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.mapper.CarMapper;
import br.com.solutis.locadora.model.dto.CarDto;
import br.com.solutis.locadora.model.entity.CarEntity;
import br.com.solutis.locadora.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService extends AbstractService<CarDto> {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarDto findById(Long id) {
        return carRepository.findById(id).map(carMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("Car not found"));
    }

    @Override
    public List<CarDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<CarEntity> cars = carRepository.findAll(paging);

        return cars.stream().map(carMapper::modelToDTO).toList();
    }

    @Override
    public CarDto add(CarDto payload) {
        CarEntity salvo = carRepository.save(carMapper.dtoToModel(payload));
        return carMapper.modelToDTO(salvo);
    }

    @Override
    public CarDto update(CarDto payload) {
        return carRepository.findById(payload.getId()).map(car -> {
            carRepository.save(carMapper.dtoToModel(payload));
            return carMapper.modelToDTO(car);
        }).orElseThrow(() -> new BadRequestException("Car not found"));
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
}
