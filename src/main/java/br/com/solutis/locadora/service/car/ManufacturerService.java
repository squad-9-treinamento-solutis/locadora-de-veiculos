package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.mapper.car.ManufacturerMapper;
import br.com.solutis.locadora.model.dto.car.ManufacturerDto;
import br.com.solutis.locadora.model.entity.car.Manufacturer;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.service.CrudService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ManufacturerService implements CrudService<ManufacturerDto> {
    private final CrudRepository<Manufacturer> manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    public ManufacturerDto findById(Long id){return  null;}
    public List<ManufacturerDto> findAll() {
        return null;
    }

    public ManufacturerDto add(ManufacturerDto payload) {
        Manufacturer manufacturer = manufacturerRepository.save(manufacturerMapper.dtoToModel(payload));
        return manufacturerMapper.modelToDTO(manufacturer);
    }

    public ManufacturerDto update(ManufacturerDto payload) {
        return null;
    }

    public void deleteById(Long id) {
        manufacturerRepository.deleteById(id);
    }
}