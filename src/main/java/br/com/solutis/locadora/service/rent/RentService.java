package br.com.solutis.locadora.service.rent;

import br.com.solutis.locadora.exception.car.CarAlreadyRentedException;
import br.com.solutis.locadora.exception.car.CarException;
import br.com.solutis.locadora.exception.car.CarNotRentedException;
import br.com.solutis.locadora.exception.person.driver.DriverException;
import br.com.solutis.locadora.exception.person.driver.DriverNotAuthorizedException;
import br.com.solutis.locadora.exception.rent.RentAlreadyConfirmedException;
import br.com.solutis.locadora.exception.rent.RentException;
import br.com.solutis.locadora.exception.rent.RentNotConfirmedException;
import br.com.solutis.locadora.exception.rent.RentNotFoundException;
import br.com.solutis.locadora.mapper.GenericMapper;
import br.com.solutis.locadora.model.dto.rent.RentDto;
import br.com.solutis.locadora.model.dto.rent.RentDtoResponse;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.model.entity.rent.InsurancePolicy;
import br.com.solutis.locadora.model.entity.rent.Rent;
import br.com.solutis.locadora.repository.car.CarRepository;
import br.com.solutis.locadora.repository.person.DriverRepository;
import br.com.solutis.locadora.repository.rent.InsurancePolicyRepository;
import br.com.solutis.locadora.repository.rent.RentRepository;
import br.com.solutis.locadora.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentService.class);

    private final RentRepository rentRepository;
    private final GenericMapper<RentDto, Rent> modelMapper;
    private final GenericMapper<RentDtoResponse, Rent> modelMapperResponse;
    private final InsurancePolicyRepository insurancePolicyRepository;
    private final CarRepository carRepository;
    private final DriverRepository driverRepository;

    public RentDtoResponse findById(Long id) {
        LOGGER.info("Finding rent with ID: {}", id);

        Rent rent = getRent(id);

        return modelMapperResponse.mapModelToDto(rent, RentDtoResponse.class);
    }

    public PageResponse<RentDtoResponse> findAll(int pageNo, int pageSize) {
        try {
            LOGGER.info("Finding all rents");

            Page<Rent> pagedRents = rentRepository.findAll(PageRequest.of(pageNo, pageSize));

            List<RentDtoResponse> rentDtos = modelMapper.mapList(pagedRents.getContent(), RentDtoResponse.class);

            PageResponse<RentDtoResponse> pageResponse = new PageResponse<>();
            pageResponse.setContent(rentDtos);
            pageResponse.setCurrentPage(pagedRents.getNumber());
            pageResponse.setTotalItems(pagedRents.getTotalElements());
            pageResponse.setTotalPages(pagedRents.getTotalPages());

            return pageResponse;
        } catch (Exception e) {
            LOGGER.error("An error occurred while finding all rents.", e);
            throw new RuntimeException("An error occurred while finding all rents.", e);
        }
    }

    public RentDtoResponse add(RentDto payload) {
        try {
            InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(payload.getInsurancePolicyId()).orElseThrow();
            Car car = carRepository.findById(payload.getCarId()).orElseThrow();
            Driver driver = driverRepository.findById(payload.getDriverId()).orElseThrow();

            if (car.isRented()) {
                throw new CarAlreadyRentedException(car.getId());
            }

            payload.setCartId(driver.getCart().getId());
            rentalCalculator(payload, car.getDailyValue(), insurancePolicy.getFranchiseValue());

            Rent rent = rentRepository.save(modelMapper.mapDtoToModel(payload, Rent.class));

            return modelMapperResponse.mapModelToDto(rent, RentDtoResponse.class);
        } catch (CarAlreadyRentedException e) {
            LOGGER.error("An error occurred while adding rent.", e);
            throw new CarException("Car with ID " + payload.getCarId() + " is already rented.", e);
        } catch (Exception e) {
            LOGGER.error("An error occurred while adding rent.", e);
            throw new RentException("An error occurred while adding rent.", e);
        }
    }

    public RentDtoResponse confirmRent(Long id) {
        LOGGER.info("Confirming rent with id {}", id);

        Rent rent = getRent(id);

        try {
            if (rent.isConfirmed()) {
                throw new RentAlreadyConfirmedException(id);
            } else if (rent.isDeleted()) {
                throw new RentNotFoundException(id);
            } else if (rent.getCar().isRented()) {
                throw new CarAlreadyRentedException(rent.getCar().getId());
            }

            rent.setConfirmed(true);
            rent.getCar().setRented(true);

            return modelMapperResponse.mapModelToDto(rentRepository.save(rent), RentDtoResponse.class);
        } catch (RentAlreadyConfirmedException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", id, e);
            throw new RentException("Rent with ID " + id + " is already confirmed.", e);
        } catch (RentNotFoundException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", id, e);
            throw new RentException("Rent with ID " + id + " not found.", e);
        } catch (CarAlreadyRentedException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", id, e);
            throw new CarException("Car with ID " + rent.getCar().getId() + " is already rented.", e);
        } catch (Exception e) {
            LOGGER.error("An error occurred while confirming rent with id {}", id, e);
            throw new RentException("An error occurred while confirming rent.", e);
        }
    }

    public RentDtoResponse finishRent(long driverId, long rentId) {
        LOGGER.info("Finishing rent with ID: {}", rentId);

        Rent rent = getRent(rentId);

        try {
            if (!rent.isConfirmed()) {
                throw new RentNotConfirmedException(rentId);
            } else if (rent.isDeleted()) {
                throw new RentNotFoundException(rentId);
            } else if (!rent.getCar().isRented()) {
                throw new CarNotRentedException(rent.getCar().getId());
            } else if (rent.getDriver().getId() != driverId) {
                throw new DriverNotAuthorizedException(driverId);
            }

            rent.setFinished(true);
            rent.setFinishedDate(LocalDate.now());
            rent.getCar().setRented(false);

            return modelMapperResponse.mapModelToDto(rentRepository.save(rent), RentDtoResponse.class);
        } catch (RentNotConfirmedException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", rentId, e);
            throw new RentException("Rent with ID " + rentId + " is not confirmed.", e);
        } catch (RentNotFoundException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", rentId, e);
            throw new RentException("Rent with ID " + rentId + " not found.", e);
        } catch (CarNotRentedException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", rentId, e);
            throw new CarException("Car with ID " + rent.getCar().getId() + " is not rented.", e);
        } catch (DriverNotAuthorizedException e) {
            LOGGER.error("An error occurred while confirming rent with id {}", driverId, e);
            throw new DriverException("Driver with ID " + driverId + " is not authorized.", e);
        } catch (Exception e) {
            LOGGER.error("An error occurred while confirming rent with id {}", rentId, e);
            throw new RentException("An error occurred while confirming rent.", e);
        }
    }

    public List<RentDtoResponse> findActiveRents(){
        List<Rent> activeRents = rentRepository.findByFinishedFalse();

        try {
            return modelMapperResponse.mapList(activeRents, RentDtoResponse.class);
        } catch (Exception e) {
            throw new RentException("An error occurred while finding active rents.", e);
        }
    }

    public List<RentDtoResponse> findFinishedRents() {
        List<Rent> finishedRents = rentRepository.findByFinishedTrue();

        try {
            return modelMapperResponse.mapList(finishedRents, RentDtoResponse.class);
        } catch (Exception e) {
            throw new RentException("An error occurred while finding finished rents.", e);
        }
    }

    public RentDtoResponse update(RentDto payload) {
        try {
            LOGGER.info("Updating rent with ID: {}", payload.getId());

            Rent rent = getRent(payload.getId());
            if (rent.isConfirmed()) {
                throw new Exception("This rent is already confirmed.");
            } else if (rent.isDeleted()) {
                throw new RentNotFoundException(payload.getId());
            }

            updateFields(rent, payload);

            return modelMapperResponse.mapModelToDto(rentRepository.save(rent), RentDtoResponse.class);
        } catch (Exception e) {
            LOGGER.error("An error occurred while updating rent with ID: {}", payload.getId(), e);
            throw new RentException("An error occurred while updating rent.", e);
        }
    }

    public void deleteById(Long id) {
        LOGGER.info("Deleting rent with ID: {}", id);

        Rent rent = getRent(id);

        try {

            rent.setDeleted(true);
            rent.getCar().setRented(false);

            rentRepository.save(rent);
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting rent with ID: {}", id, e);
            throw new RentException("An error occurred while deleting rent.", e);
        }
    }

    private void rentalCalculator(RentDto payload, BigDecimal dailyValue, BigDecimal franchiseValue) throws Exception {
        long daysBetween = ChronoUnit.DAYS.between(payload.getStartDate(), payload.getEndDate());
        daysBetween = daysBetween == 0 ? 1 : daysBetween;

        if (daysBetween < 1) {
            throw new Exception("The rental period must be at least one day.");
        }

        BigDecimal daysBetweenDecimal = new BigDecimal(String.valueOf(daysBetween));
        BigDecimal rentTotal = dailyValue.multiply(daysBetweenDecimal).add(franchiseValue);

        payload.setValue(rentTotal);
    }

    private Rent getRent(Long id) {
        return rentRepository.findById(id).orElseThrow(() -> new RentNotFoundException(id));
    }

    private void updateFields(Rent rent, RentDto payload) {
        rent.setStartDate(payload.getStartDate());
        rent.setEndDate(payload.getEndDate());
        rent.setValue(payload.getValue());
    }
}
