package br.com.solutis.locadora.service.car;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.exception.car.ModelException;
import br.com.solutis.locadora.mapper.car.ModelMapper;
import br.com.solutis.locadora.model.dto.car.ModelDto;
import br.com.solutis.locadora.model.entity.car.Model;
import br.com.solutis.locadora.repository.CrudRepository;
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
public class ModelService implements CrudService<ModelDto> {
    private final CrudRepository<Model> modelRepository;
    private final ModelMapper modelMapper;

    @Override
    public ModelDto findById(Long id) {
        return modelRepository.findById(id).map(modelMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("Model Not found"));
    }

    @Override
    public List<ModelDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Model> models = modelRepository.findAll(paging);

        return modelMapper.listModelToListDto(models);
    }

    @Override
    public ModelDto add(ModelDto payload) {
        try{
            Model model = modelRepository.save(modelMapper.dtoToModel(payload));

            return modelMapper.modelToDTO(model);
        }catch (Exception e){
            throw new ModelException("An error occurred while adding the car model", e);
        }
    }

    @Override
    public ModelDto update(ModelDto payload) {
        findById(payload.getId());

        try {
            Model model = modelRepository
                    .save(modelMapper.dtoToModel(payload));

            return modelMapper.modelToDTO(model);
        } catch (Exception e) {
            throw new ModelException("An error occurred while updating the car model.", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        try{
            modelRepository.deleteById(id);
        }catch (Exception e){
            throw new ModelException("An error occurred while deleting the car model", e);
        }

    }
}
