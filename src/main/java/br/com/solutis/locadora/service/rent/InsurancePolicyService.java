package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.rent.InsurancePolicyException;
import br.com.solutis.locadora.exception.rent.InsurancePolicyNotFoundException;
import br.com.solutis.locadora.mapper.rent.InsurancePolicyMapper;
import br.com.solutis.locadora.model.dto.rent.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
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
public class InsurancePolicyService implements CrudService<InsurancePolicyDto> {
    private final CrudRepository<InsurancePolicy> insurancePolicyRepository;
    private final InsurancePolicyMapper insurancePolicyMapper;

    public InsurancePolicyDto findById(Long id) {
        return insurancePolicyRepository.findById(id).map(insurancePolicyMapper::modelToDTO)
                .orElseThrow(() -> new InsurancePolicyNotFoundException(id));
    }

    public PageResponse<InsurancePolicyDto> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<InsurancePolicy> pagedInsurancePolicies = insurancePolicyRepository.findAll(paging);

        List<InsurancePolicyDto> insurancePolicyDtos = insurancePolicyMapper.listModelToListDto(pagedInsurancePolicies.getContent());

        PageResponse<InsurancePolicyDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(insurancePolicyDtos);
        pageResponse.setCurrentPage(pagedInsurancePolicies.getNumber());
        pageResponse.setTotalItems(pagedInsurancePolicies.getTotalElements());
        pageResponse.setTotalPages(pagedInsurancePolicies.getTotalPages());

        return pageResponse;
    }

    public InsurancePolicyDto add(InsurancePolicyDto payload) {
        try {
            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(insurancePolicyMapper.dtoToModel(payload));

            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        } catch (Exception e) {
            throw new InsurancePolicyException("An error occurred while adding insurance policy.", e);
        }
    }

    public InsurancePolicyDto update(InsurancePolicyDto payload) {
        findById(payload.getId());

        try {
            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(insurancePolicyMapper.dtoToModel(payload));

            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        } catch (Exception e) {
            throw new InsurancePolicyException("An error occurred while updating insurance policy.", e);
        }
    }

    public void deleteById(Long id) {
        findById(id);

        try {
            insurancePolicyRepository.deleteById(id);
        } catch (Exception e) {
            throw new InsurancePolicyException("An error occurred while deleting insurance policy.", e);
        }
    }
}
