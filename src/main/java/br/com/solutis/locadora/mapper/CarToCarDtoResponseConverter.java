package br.com.solutis.locadora.mapper;

import br.com.solutis.locadora.model.dto.car.CarDtoResponse;
import br.com.solutis.locadora.model.entity.car.Car;
import org.modelmapper.AbstractConverter;



public class CarToCarDtoResponseConverter extends AbstractConverter<Car, CarDtoResponse> {
    @Override
    protected CarDtoResponse convert(Car car) {
        CarDtoResponse carDtoResponse = new CarDtoResponse();

        carDtoResponse.setId(car.getId());
        carDtoResponse.setColor(car.getColor());
        carDtoResponse.setPlate(car.getPlate());
        carDtoResponse.setChassis(car.getChassis());
        carDtoResponse.setDailyValue(car.getDailyValue());
        carDtoResponse.setRented(car.isRented());

        carDtoResponse.setAccessories(car.getAccessories());

        if (car.getModel() != null) {
            carDtoResponse.setDescription(car.getModel().getDescription());
            carDtoResponse.setCategory(car.getModel().getCategory());
            if (car.getModel().getManufacturer() != null) {
                carDtoResponse.setName(car.getModel().getManufacturer().getName());
            }
        }

        return carDtoResponse;
    }

}
