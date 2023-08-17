package br.com.solutis.locadora.model.dto.car;

import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ModelDto {
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters long")
    private String description;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private ModelCategoryEnum category;

    @NotNull(message = "Manufacturer is required")
    private Long manufacturerId;
    private Long id;
}
