package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
import br.com.solutis.locadora.model.entity.rent.Rent;
import br.com.solutis.locadora.repository.car.CarRepository;
import br.com.solutis.locadora.repository.person.DriverRepository;
import br.com.solutis.locadora.repository.rent.InsurancePolicyRepository;
import br.com.solutis.locadora.repository.rent.RentRepository;
import br.com.solutis.locadora.response.PageResponse;
import br.com.solutis.locadora.service.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RentService implements CrudService<RentDto> {

    private final RentRepository rentRepository;
    private final GenericMapper<RentDto, Rent> modelMapper;
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    public RentDto findById(Long id) {
        return null;
    }

    public PageResponse<RentDto> findAll(int pageNo, int pageSize) {
        return null;
    }

    public RentDto add(RentDto payload) {
        try {

            InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(payload.getInsurancePolicyId()).orElseThrow();
            Car car = carRepository.findById(payload.getCarId()).orElseThrow();
            Driver driver = driverRepository.findById(payload.getDriverId()).orElseThrow();

            payload.setCartId(driver.getCart().getId());
            rentalCalculator(payload, car.getDailyValue(), insurancePolicy.getFranchiseValue());
            car.setRented(true);

            Rent rent = rentRepository.save(modelMapper.mapDtoToModel(payload, Rent.class));

            return modelMapper.mapModelToDto(rent, RentDto.class);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while adding rent.", e);
        }
    }

    public RentDto update(RentDto payload) {
        return null;
    }

    public void deleteById(Long id) {
    }

    private void rentalCalculator(RentDto payload, BigDecimal dailyValue, BigDecimal franchiseValue) {
        long daysBetween = ChronoUnit.DAYS.between(payload.getStartDate(), payload.getEndDate());
        BigDecimal daysBetweenDecimal = new BigDecimal(String.valueOf(daysBetween));
        BigDecimal rentTotal = dailyValue.multiply(daysBetweenDecimal).add(franchiseValue);

        payload.setValue(rentTotal);

    }
}
