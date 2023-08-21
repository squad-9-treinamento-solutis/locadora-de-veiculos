package br.com.solutis.locadora.model.dto.rent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CartDto {
    private Long id;

    @NotNull(message = "Driver is required")
    private long driverId;

    private List<Long> rentsIds;
}
