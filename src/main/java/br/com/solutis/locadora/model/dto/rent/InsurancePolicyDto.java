package br.com.solutis.locadora.model.dto.rent;

import br.com.solutis.locadora.model.entity.rent.Rent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class InsurancePolicyDto {
    private Long id;

    @NotNull(message = "Franchise value is required")
    private BigDecimal franchiseValue;

    private boolean thirdPartyCoverage = false;

    private boolean naturalPhenomenaCoverage = false;

    private boolean theftCoverage = false;

    private Rent rent;
}
