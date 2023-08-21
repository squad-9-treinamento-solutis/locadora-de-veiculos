package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.rent.insurace.InsurancePolicyException;
import br.com.solutis.locadora.exception.rent.insurace.InsurancePolicyNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.rent.InsurancePolicyDto;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
import br.com.solutis.locadora.repository.rent.InsurancePolicyRepository;
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
public class InsurancePolicyService implements CrudService<InsurancePolicyDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsurancePolicyService.class);
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final GenericMapper<InsurancePolicyDto, InsurancePolicy> modelMapper;

    public InsurancePolicyDto findById(Long id) {
        LOGGER.info("Finding insurance policy with ID: {}", id);
        InsurancePolicy insurancePolicy = getInsurancePolicy(id);

        return modelMapper.mapModelToDto(insurancePolicy, InsurancePolicyDto.class);
    }

    public PageResponse<InsurancePolicyDto> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Fetching insurance policies with page number {} and page size {}.", pageNo, pageSize);

            Pageable paging = PageRequest.of(pageNo, pageSize);
            Page<InsurancePolicy> pagedInsurancePolicies = insurancePolicyRepository.findByDeletedFalse(paging);

            List<InsurancePolicyDto> insurancePolicyDtos = modelMapper.
                    mapList(pagedInsurancePolicies.getContent(), InsurancePolicyDto.class);

            PageResponse<InsurancePolicyDto> pageResponse = new PageResponse<>();
            pageResponse.setContent(insurancePolicyDtos);
            pageResponse.setCurrentPage(pagedInsurancePolicies.getNumber());
            pageResponse.setTotalItems(pagedInsurancePolicies.getTotalElements());
            pageResponse.setTotalPages(pagedInsurancePolicies.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while fetching insurance policies.", e);
        }
    }

    public InsurancePolicyDto add(InsurancePolicyDto payload) {
        try {
            LOGGER.info("Adding insurance policy: {}", payload);

            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(modelMapper.mapDtoToModel(payload, InsurancePolicy.class));

            return modelMapper.mapModelToDto(insurancePolicy, InsurancePolicyDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while adding insurance policy.", e);
        }
    }

    public InsurancePolicyDto update(InsurancePolicyDto payload) {
        InsurancePolicy existingInsurancePolicy = getInsurancePolicy(payload.getId());
        if (existingInsurancePolicy.isDeleted())
            throw new InsurancePolicyNotFoundException(existingInsurancePolicy.getId());

        try {
            LOGGER.info("Updating insurance policy: {}", payload);
            InsurancePolicyDto insurancePolicyDto = modelMapper
                    .mapModelToDto(existingInsurancePolicy, InsurancePolicyDto.class);

            updateModelFields(payload, insurancePolicyDto);

            InsurancePolicy insurancePolicy = insurancePolicyRepository
                    .save(modelMapper.mapDtoToModel(insurancePolicyDto, InsurancePolicy.class));

            return modelMapper.mapModelToDto(insurancePolicy, InsurancePolicyDto.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while updating insurance policy.", e);
        }
    }

    public void deleteById(Long id) {
        InsurancePolicyDto insurancePolicyDto = findById(id);

        try {
            LOGGER.info("Soft deleting insurance policy with ID: {}", id);

            InsurancePolicy insurancePolicy = modelMapper.mapDtoToModel(insurancePolicyDto, InsurancePolicy.class);
            insurancePolicy.setDeleted(true);

            insurancePolicyRepository.save(insurancePolicy);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new InsurancePolicyException("An error occurred while deleting insurance policy.", e);
        }
    }

    private void updateModelFields(InsurancePolicyDto payload, InsurancePolicyDto existingInsurancePolicy) {
        if (payload.getFranchiseValue() != null) {
            existingInsurancePolicy.setFranchiseValue(payload.getFranchiseValue());
        }

        existingInsurancePolicy.setThirdPartyCoverage(payload.isThirdPartyCoverage());
        existingInsurancePolicy.setNaturalPhenomenaCoverage(payload.isNaturalPhenomenaCoverage());
        existingInsurancePolicy.setTheftCoverage(payload.isTheftCoverage());
    }

    private InsurancePolicy getInsurancePolicy(Long id) {
        return insurancePolicyRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Insurance policy with ID {} not found.", id);
            return new InsurancePolicyNotFoundException(id);
        });
    }
}
