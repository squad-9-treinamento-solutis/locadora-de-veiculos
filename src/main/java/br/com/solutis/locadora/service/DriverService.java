package br.com.solutis.locadora.service;

import br.com.solutis.locadora.mapper.DriverMapper;
import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.Driver;
import br.com.solutis.locadora.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class DriverService implements CrudService<DriverDto>{
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverDto findById(Long id) {
        return null;
    }

    public List<DriverDto> findAll() {
        return null;
    }

    public DriverDto add(DriverDto payload) {
        Driver driver = driverRepository.save(driverMapper.dtoToModel(payload));
        return driverMapper.modelToDTO(driver);
    }

    public DriverDto update(DriverDto payload) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
