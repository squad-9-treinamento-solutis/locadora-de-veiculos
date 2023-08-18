package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.mapper.car.ManufacturerMapper;
import br.com.solutis.locadora.model.dto.car.ManufacturerDto;
import br.com.solutis.locadora.model.entity.car.Manufacturer;
import br.com.solutis.locadora.repository.CrudRepository;
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
public class ManufacturerService implements CrudService<ManufacturerDto> {
    private final CrudRepository<Manufacturer> manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public ManufacturerDto findById(Long id) {
        return manufacturerRepository.findById(id).map(manufacturerMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("ManufacturerMapper not found. ID: " + id));
    }

    @Override
    public PageResponse<ManufacturerDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Manufacturer> pagedManufacturers = manufacturerRepository.findAll(paging);

        List<ManufacturerDto> manufacturerDtos = manufacturerMapper.listModelToListDto(pagedManufacturers.getContent());

        PageResponse<ManufacturerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(manufacturerDtos);
        pageResponse.setCurrentPage(pagedManufacturers.getNumber());
        pageResponse.setTotalItems(pagedManufacturers.getTotalElements());
        pageResponse.setTotalPages(pagedManufacturers.getTotalPages());

        return pageResponse;
    }

    @Override
    public ManufacturerDto add(ManufacturerDto payload) {
        Manufacturer manufacturer = manufacturerRepository.save(manufacturerMapper.dtoToModel(payload));
        return manufacturerMapper.modelToDTO(manufacturer);
    }

    @Override
    public ManufacturerDto update(ManufacturerDto payload) {
        return manufacturerRepository.findById(payload.getId()).map(manufacturer -> {
            manufacturerRepository.save(manufacturerMapper.dtoToModel(payload));

            return manufacturerMapper.modelToDTO(manufacturer);
        }).orElseThrow(() -> new BadRequestException("Car Not found"));
    }

    @Override
    public void deleteById(Long id) {
        manufacturerRepository.deleteById(id);
    }
}