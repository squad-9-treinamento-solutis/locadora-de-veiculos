package br.com.solutis.locadora.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends PersonDto {
    @NotNull(message = "Registration is required")
    @NotBlank(message = "Registration is required")
    @Size( max = 255, message = "Registration must be less than 255 characters long")
    private String registration;
}
