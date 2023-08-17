package br.com.solutis.locadora.model.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class CarDto {
    private Long id;

    @NotNull(message = "Plate is required")
    @NotBlank(message = "Plate is required")
    @Size(min = 1, max = 255, message = "Plate must be between 1 and 255 characters long")
    private String plate;

    @NotNull(message = "Chassis is required")
    @NotBlank(message = "Chassis is required")
    @Size(min = 1, max = 255, message = "Chassis must be between 1 and 255 characters long")
    private String chassis;

    @NotNull(message = "Color is required")
    @NotBlank(message = "Color is required")
    private String color;

    @NotNull(message = "Daily value is required")
    private BigDecimal dailyValue;

    @NotNull(message = "Image URL is required")
    @NotBlank(message = "Image URL is required")
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

    @NotNull(message = "Model is required")
    private Long modelId;

    private Long[] accessoriesIds;
}
