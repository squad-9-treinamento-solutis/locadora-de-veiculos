package br.com.solutis.locadora.model.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DriverDto extends PersonDto {
    @NotNull(message = "CNH is required")
    @NotBlank(message = "CNH is required")
    @Size(max = 10, message = "CNH must be less than 255 characters long")
    private String cnh;
}
