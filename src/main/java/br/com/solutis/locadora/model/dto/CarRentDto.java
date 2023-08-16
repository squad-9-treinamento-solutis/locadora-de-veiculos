package br.com.solutis.locadora.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class CarRentDto extends AbstractDto {
    @NotNull(message = "Rent date is required")
    private Date rentDate;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotNull(message = "Value is required")
    private BigDecimal value;

    @NotNull(message = "Insurance policy is required")
    private long insurancePolicyId;

    @NotNull(message = "Driver is required")
    private long driverId;

    @NotNull(message = "Car is required")
    private long carId;
}
