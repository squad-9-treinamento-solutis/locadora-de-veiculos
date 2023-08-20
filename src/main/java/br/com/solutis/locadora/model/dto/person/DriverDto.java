package br.com.solutis.locadora.model.dto.person;

import br.com.solutis.locadora.model.entity.person.GenderEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DriverDto {
    private Long id;

    @NotNull(message = "CNH is required")
    @NotBlank(message = "CNH is required")
    @Size(max = 10, message = "CNH must be less than 10 characters long")
    @Size(max = 255, message = "CNH must be less than 255 characters long")
    private String cnh;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters long")
    private String name;

    @NotNull(message = "CPF is required")
    @NotBlank(message = "CPF is required")
    @Size(min = 14, max = 14, message = "CPF must be 14 characters long")
    private String cpf;

    @NotNull(message = "Birth Date is required")

    private LocalDate birthDate;

    @NotNull(message = "Gender is required")
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;
}
