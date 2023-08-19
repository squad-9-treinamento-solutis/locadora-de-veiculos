package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.ModelException;
import br.com.solutis.locadora.exception.car.ModelNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.model.entity.car.Model;
import br.com.solutis.locadora.repository.CrudRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ModelService implements CrudService<ModelDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);
    private final CrudRepository<Model> modelRepository;
    private final GenericMapper<ModelDto, Model> modelMapper;

    public ModelDto findById(Long id) {
        LOGGER.info("Finding model with ID: {}", id);

        Model model = modelRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Model with ID {} not found.", id);
            return new ModelNotFoundException(id);
        });

        return modelMapper.mapModelToDto(model, ModelDto.class);
    }

    public PageResponse<ModelDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching models with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Model> pagedModels = modelRepository.findAll(paging);

            List<ModelDto> manufacturerDtos = modelMapper
                    .mapList(pagedModels.getContent(), ModelDto.class);

            PageResponse<ModelDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(manufacturerDtos);
            pageResponse.setCurrentPage(pagedModels.getNumber());
            pageResponse.setTotalItems(pagedModels.getTotalElements());
            pageResponse.setTotalPages(pagedModels.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while fetching models.", e);
        }
    }

    public ModelDto add(ModelDto payload) {
        try{
            LOGGER.info("Adding model: {}", payload);

            Model model = modelRepository.save(modelMapper.mapDtoToModel(payload, Model.class));

            return modelMapper.mapModelToDto(model, ModelDto.class);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while adding the car model", e);
        }
    }

    public ModelDto update(ModelDto payload) {
        findById(payload.getId());

        try {
            LOGGER.info("Updating model: {}", payload);

            Model model = modelRepository
                    .save(modelMapper.mapDtoToModel(payload, Model.class));

            return modelMapper.mapModelToDto(model, ModelDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while updating the car model.", e);
        }
    }

    public void deleteById(Long id) {
        ModelDto modelDto = findById(id);
        try{
            LOGGER.info("Soft deleting model with ID {}", id);

            Model model = modelMapper.mapDtoToModel(modelDto, Model.class);
            model.setDeleted(true);

            modelRepository.save(model);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while deleting the car model", e);
        }
    }
}
