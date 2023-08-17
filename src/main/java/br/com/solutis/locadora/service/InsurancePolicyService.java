package br.com.solutis.locadora.service;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.mapper.InsurancePolicyMapper;
import br.com.solutis.locadora.model.dto.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.InsurancePolicyEntity;
import br.com.solutis.locadora.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class InsurancePolicyService extends AbstractService<InsurancePolicyDto>{
    private final GenericRepository<InsurancePolicyEntity> insurancePolicyRepository;
    private final InsurancePolicyMapper insurancePolicyMapper;

    @Override
    public InsurancePolicyDto findById(Long id) {
        return insurancePolicyRepository.findById(id).map(insurancePolicyMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("Insurance Policy Not found"));
    }

    @Override
    public List<InsurancePolicyDto> findAll() {
        return insurancePolicyMapper.listModelToListDto(insurancePolicyRepository.findAll());
    }

    @Override
    public InsurancePolicyDto add(InsurancePolicyDto payload) {
        InsurancePolicyEntity insurancePolicyEntity = insurancePolicyRepository
                .save(insurancePolicyMapper.dtoToModel(payload));

        return insurancePolicyMapper.modelToDTO(insurancePolicyEntity);
    }

    @Override
    public InsurancePolicyDto update(InsurancePolicyDto payload) {
        return insurancePolicyRepository.findById(payload.getId()).map(insurancePolicy -> {
            insurancePolicyRepository.save(insurancePolicyMapper.dtoToModel(payload));
            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        }).orElseThrow(() -> new BadRequestException("Insurance Policy Not found"));
    }

    @Override
    public void deleteById(Long id) {
        insurancePolicyRepository.deleteById(id);
    }
}
