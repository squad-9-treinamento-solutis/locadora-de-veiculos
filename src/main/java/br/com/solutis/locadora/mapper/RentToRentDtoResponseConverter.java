package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.rent.RentDtoResponse;
import br.com.solutis.locadora.model.entity.rent.Rent;
import org.modelmapper.AbstractConverter;

public class RentToRentDtoResponseConverter extends AbstractConverter<Rent, RentDtoResponse> {
    @Override
    protected RentDtoResponse convert(Rent rent) {

        RentDtoResponse rentDtoResponse = new RentDtoResponse();

        rentDtoResponse.setId(rent.getId());
        rentDtoResponse.setCpf(rent.getDriver().getCpf());
        rentDtoResponse.setNameDriver(rent.getDriver().getName());
        rentDtoResponse.setStartDate(rent.getStartDate());
        rentDtoResponse.setEndDate(rent.getEndDate());
        rentDtoResponse.setFinishedDate(rent.getFinishedDate());
        rentDtoResponse.setValue(rent.getValue());
        rentDtoResponse.setConfirmed(rent.isConfirmed());
        rentDtoResponse.setFinished(rent.isFinished());
        rentDtoResponse.setFranchiseValue(rent.getInsurancePolicy().getFranchiseValue());
        rentDtoResponse.setThirdPartyCoverage(rent.getInsurancePolicy().isThirdPartyCoverage());
        rentDtoResponse.setNaturalPhenomenaCoverage(rent.getInsurancePolicy().isNaturalPhenomenaCoverage());
        rentDtoResponse.setTheftCoverage(rent.getInsurancePolicy().isTheftCoverage());
        rentDtoResponse.setPlate(rent.getCar().getPlate());
        rentDtoResponse.setColor(rent.getCar().getColor());
        rentDtoResponse.setModel(rent.getCar().getModel().getDescription());
        rentDtoResponse.setCategory(rent.getCar().getModel().getCategory());
        rentDtoResponse.setManufacturer(rent.getCar().getModel().getManufacturer().getName());

        return rentDtoResponse;
    }
}
