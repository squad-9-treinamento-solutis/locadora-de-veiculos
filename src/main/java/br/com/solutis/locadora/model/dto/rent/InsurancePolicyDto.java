package br.com.solutis.locadora.model.dto.rent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class InsurancePolicyDto {
    @NotNull(message = "Franchise value is required")
    private BigDecimal franchiseValue;

    private boolean thirdPartyCoverage = false;

    private boolean naturalPhenomenaCoverage = false;

    private boolean theftCoverage = false;
    private Long id;
}
