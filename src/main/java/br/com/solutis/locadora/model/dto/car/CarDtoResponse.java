package br.com.solutis.locadora.model.dto.car;

import br.com.solutis.locadora.model.entity.car.Accessory;
import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode
public class CarDtoResponse {

        private Long id;

        private String imageUrl;

        private String description;

        private String color;

        private String name;

        private ModelCategoryEnum category;

        private String plate;

        private String chassis;

        private BigDecimal dailyValue;

        private boolean rented;

        private List<Accessory> accessories;


}
