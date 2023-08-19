package br.com.solutis.locadora.model.entity.rent;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "insurance_policies")
public class InsurancePolicy extends AbstractEntity {
    @Column(name = "franchise_value", nullable = false)
    private BigDecimal franchiseValue;

    @Column(name = "third_party_coverage")
    private boolean thirdPartyCoverage = false;

    @Column(name = "natural_phenomena_coverage")
    private boolean naturalPhenomenaCoverage = false;

    @Column(name = "theft_coverage")
    private boolean theftCoverage = false;

    @OneToOne(mappedBy = "insurancePolicy")
    private Rent rent;
}
