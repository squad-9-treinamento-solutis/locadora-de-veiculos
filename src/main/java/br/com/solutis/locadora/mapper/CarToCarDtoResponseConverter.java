package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.car.CarDtoResponse;
import br.com.solutis.locadora.model.entity.car.Car;
import org.modelmapper.AbstractConverter;



public class CarToCarDtoResponseConverter extends AbstractConverter<Car, CarDtoResponse> {
    @Override
    protected CarDtoResponse convert(Car car) {
        CarDtoResponse dto = new CarDtoResponse();

        dto.setId(car.getId());
        dto.setColor(car.getColor());
        dto.setPlate(car.getPlate());
        dto.setChassis(car.getChassis());
        dto.setDailyValue(car.getDailyValue());
        dto.setRented(car.isRented());

        dto.setAccessories(car.getAccessories());

        if (car.getModel() != null) {
            dto.setDescription(car.getModel().getDescription());
            dto.setCategory(car.getModel().getCategory());
            if (car.getModel().getManufacturer() != null) {
                dto.setName(car.getModel().getManufacturer().getName());
            }
        }

        return dto;
    }

}
