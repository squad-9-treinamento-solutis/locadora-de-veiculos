package br.com.solutis.locadora.service;

import br.com.solutis.locadora.mapper.DriverMapper;
import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.DriverEntity;
import br.com.solutis.locadora.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DriverService extends AbstractService<DriverDto>{

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DriverMapper driverMapper;

    @Override
    public DriverDto findById(Long id) {
        return null;
    }

    @Override
    public List<DriverDto> findAll() {
        return null;
    }

    @Override
    public DriverDto add(DriverDto payload) {
        DriverEntity driverEntity = driverRepository.save(driverMapper.dtoToModel(payload));
        return driverMapper.modelToDTO(driverEntity);
    }

    @Override
    public DriverDto update(DriverDto payload) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
