package br.com.solutis.locadora.service;

import br.com.solutis.locadora.mapper.DriverMapper;
import br.com.solutis.locadora.model.dto.DriverDto;
import br.com.solutis.locadora.model.entity.DriverEntity;
import br.com.solutis.locadora.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService extends AbstractService<DriverDto> {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverDto findById(Long id) {
        return null;
    }

    @Override
    public List<DriverDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<DriverEntity> drivers = driverRepository.findAll(paging);

        return drivers.stream().map(driverMapper::modelToDTO).toList();
    }

    @Override
    public DriverDto add(DriverDto payload) {
        DriverEntity driverEntity = driverRepository.save(driverMapper.dtoToModel(payload));
        driverEntity = driverRepository.save(driverEntity);
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
