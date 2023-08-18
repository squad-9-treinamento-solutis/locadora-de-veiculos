package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.rent.InsurancePolicyException;
import br.com.solutis.locadora.exception.rent.InsurancePolicyNotFoundException;
import br.com.solutis.locadora.mapper.rent.InsurancePolicyMapper;
import br.com.solutis.locadora.model.dto.rent.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
import br.com.solutis.locadora.repository.CrudRepository;
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
public class InsurancePolicyService implements CrudService<InsurancePolicyDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final CrudRepository<InsurancePolicy> insurancePolicyRepository;
    private final InsurancePolicyMapper insurancePolicyMapper;

    public InsurancePolicyDto findById(Long id) {
        LOGGER.info("Finding insurance policy with ID: {}", id);

        return insurancePolicyRepository.findById(id)
                .map(insurancePolicyMapper::modelToDTO)
                .orElseThrow(() -> {
                    LOGGER.error("Insurance policy with ID {} not found.", id);
                    return new InsurancePolicyNotFoundException(id);
                });
    }

    public List<InsurancePolicyDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching insurance policies with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<InsurancePolicy> insurancePolicies = insurancePolicyRepository.findAll(paging);

            return insurancePolicyMapper.listModelToListDto(insurancePolicies);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while fetching insurance policies.", e);
        }
    }

    public InsurancePolicyDto add(InsurancePolicyDto payload) {
        try {
            LOGGER.info("Adding insurance policy: {}", payload);

            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(insurancePolicyMapper.dtoToModel(payload));

            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while adding insurance policy.", e);
        }
    }

    public InsurancePolicyDto update(InsurancePolicyDto payload) {
        findById(payload.getId());

        try {
            LOGGER.info("Updating insurance policy: {}", payload);

            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(insurancePolicyMapper.dtoToModel(payload));

            return insurancePolicyMapper.modelToDTO(insurancePolicy);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while updating insurance policy.", e);
        }
    }

    public void deleteById(Long id) {
        findById(id);

        try {
            LOGGER.info("Deleting insurance policy with ID: {}", id);

            insurancePolicyRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while deleting insurance policy.", e);
        }
    }
}
