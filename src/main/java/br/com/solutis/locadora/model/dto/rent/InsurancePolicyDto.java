package br.com.solutis.locadora.model.dto.rent;

import br.com.solutis.locadora.model.dto.AbstractDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class InsurancePolicyDto extends AbstractDto {
    @NotNull(message = "Franchise value is required")
    private BigDecimal franchiseValue;

    private boolean thirdPartyCoverage = false;

    private boolean naturalPhenomenaCoverage = false;

    private boolean theftCoverage = false;
}
