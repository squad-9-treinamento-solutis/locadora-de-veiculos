package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.car.ModelException;
import br.com.solutis.locadora.exception.car.ModelNotFoundException;
import br.com.solutis.locadora.mapper.car.ModelMapper;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.model.entity.car.Model;
import br.com.solutis.locadora.repository.CrudRepository;
import br.com.solutis.locadora.service.CrudService;
import br.com.solutis.locadora.service.rent.InsurancePolicyService;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<Model> modelRepository;
    private final ModelMapper modelMapper;

    @Override
    public ModelDto findById(Long id) {
        LOGGER.info("Finding model with ID: {}", id);

        return modelRepository.findById(id)
                .map(modelMapper::modelToDTO)
                .orElseThrow(() -> {
                    LOGGER.error("Model with ID {} not found.", id);
                    return new ModelNotFoundException(id);
                });
    }

    @Override
    public List<ModelDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching models with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<Model> models = modelRepository.findAll(paging);

            return modelMapper.listModelToListDto(models);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while fetching models.", e);
        }
    }

    @Override
    public ModelDto add(ModelDto payload) {
        try{
            LOGGER.info("Adding model: {}", payload);

            Model model = modelRepository.save(modelMapper.dtoToModel(payload));

            return modelMapper.modelToDTO(model);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while adding the car model", e);
        }
    }

    @Override
    public ModelDto update(ModelDto payload) {
        findById(payload.getId());

        try {
            LOGGER.info("Updating model: {}", payload);

            Model model = modelRepository
                    .save(modelMapper.dtoToModel(payload));

            return modelMapper.modelToDTO(model);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while updating the car model.", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id);

        try{
            LOGGER.info("Deleting model with ID {}", id);

            modelRepository.deleteById(id);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new ModelException("An error occurred while deleting the car model", e);
        }

    }
}
