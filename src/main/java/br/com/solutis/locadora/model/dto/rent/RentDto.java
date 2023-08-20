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

    @NotNull(message = "Rent date is required")
    private LocalDate rentDate;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Value is required")
    private BigDecimal value;

    @NotNull(message = "Insurance policy is required")
    private long insurancePolicyId;

    @NotNull(message = "Driver is required")
    private long driverId;

    @NotNull(message = "Car is required")
    private long carId;

    @NotNull(message = "cart is required")
    private long cartId;
}
