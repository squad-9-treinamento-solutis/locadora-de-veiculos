package br.com.solutis.locadora.model.dto.car;

import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ModelDtoResponse {

    private Long id;
    private String model;
    private ModelCategoryEnum category;
    private String manufacturer;

}
