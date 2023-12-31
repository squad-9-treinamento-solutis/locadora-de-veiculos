package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.model.ModelException;
import br.com.solutis.locadora.exception.car.model.ModelNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.model.dto.car.ModelDtoResponse;
import br.com.solutis.locadora.model.entity.car.Model;
import br.com.solutis.locadora.repository.car.ModelRepository;
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
public class ModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);
    private final ModelRepository modelRepository;
    private final GenericMapper<ModelDto, Model> modelMapper;
    private final GenericMapper<ModelDtoResponse, Model> modelMapperResponse;

    public ModelDtoResponse findById(Long id) {
        LOGGER.info("Finding model with ID: {}", id);
        Model model = getModel(id);

        return modelMapperResponse.mapModelToDto(model, ModelDtoResponse.class);
    }

    public PageResponse<ModelDtoResponse> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching models with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Model> pagedModels = modelRepository.findByDeletedFalse(paging);

            List<ModelDtoResponse> modelDtos = modelMapper
                    .mapList(pagedModels.getContent(), ModelDtoResponse.class);

            PageResponse<ModelDtoResponse> pageResponse = new PageResponse<>();
            pageResponse.setContent(modelDtos);
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
        try {
            LOGGER.info("Adding model: {}", payload);

            Model model = modelRepository.save(modelMapper.mapDtoToModel(payload, Model.class));

            return modelMapper.mapModelToDto(model, ModelDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while adding the car model", e);
        }
    }

    public ModelDtoResponse update(ModelDto payload) {
        Model existingModel = getModel(payload.getId());
        if (existingModel.isDeleted()) throw new ModelNotFoundException(existingModel.getId());

        try {
            LOGGER.info("Updating model: {}", payload);
            ModelDto modelDto = modelMapper
                    .mapModelToDto(existingModel, ModelDto.class);

            updateModelFields(payload, modelDto);

            Model model = modelRepository
                    .save(modelMapper.mapDtoToModel(modelDto, Model.class));

            return modelMapperResponse.mapModelToDto(model, ModelDtoResponse.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while updating the car model.", e);
        }
    }

    public void deleteById(Long id) {
        Model model = getModel(id);
        try {
            LOGGER.info("Soft deleting model with ID {}", id);


            model.setDeleted(true);

            modelRepository.save(model);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while deleting the car model", e);
        }
    }

    private void updateModelFields(ModelDto payload, ModelDto existingModel) {
        if (payload.getDescription() != null) {
            existingModel.setDescription(payload.getDescription());
        }
        if (payload.getCategory() != null) {
            existingModel.setCategory(payload.getCategory());
        }
        if (payload.getManufacturerId() != null) {
            existingModel.setManufacturerId(payload.getManufacturerId());
        }
    }

    private Model getModel(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Model with ID {} not found.", id);
            return new ModelNotFoundException(id);
        });
    }
}
