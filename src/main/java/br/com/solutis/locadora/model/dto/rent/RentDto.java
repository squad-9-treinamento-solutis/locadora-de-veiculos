package br.com.solutis.locadora.model.dto.rent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class RentDto {
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private LocalDate finishedDate;

    private BigDecimal value;

    @NotNull(message = "Insurance policy is required")
    private long insurancePolicyId;

    private long driverId;

    @NotNull(message = "Car is required")
    private long carId;

    private long cartId;
}
