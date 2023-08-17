package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.BadRequestException;
import br.com.solutis.locadora.mapper.rent.InsurancePolicyMapper;
import br.com.solutis.locadora.model.dto.rent.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
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
public class InsurancePolicyService implements CrudService<InsurancePolicyDto> {
    private final CrudRepository<InsurancePolicy> insurancePolicyRepository;
    private final InsurancePolicyMapper insurancePolicyMapper;

    public InsurancePolicyDto findById(Long id) {
        return insurancePolicyRepository.findById(id).map(insurancePolicyMapper::modelToDTO)
                .orElseThrow(() -> new BadRequestException("Insurance Policy Not found"));
    }

    public List<InsurancePolicyDto> findAll() {
        return insurancePolicyMapper.listModelToListDto(insurancePolicyRepository.findAll());
    }

    public InsurancePolicyDto add(InsurancePolicyDto payload) {
        InsurancePolicy insurancePolicy = insurancePolicyRepository
                .save(insurancePolicyMapper.dtoToModel(payload));

        return insurancePolicyMapper.modelToDTO(insurancePolicy);
    }

    public InsurancePolicyDto update(InsurancePolicyDto payload) {
        return insurancePolicyRepository.findById(payload.getId()).map(insurancePolicy -> {
            insurancePolicyRepository.save(insurancePolicyMapper.dtoToModel(payload));
            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        }).orElseThrow(() -> new BadRequestException("Insurance Policy Not found"));
    }

    public void deleteById(Long id) {
        insurancePolicyRepository.deleteById(id);
    }
}