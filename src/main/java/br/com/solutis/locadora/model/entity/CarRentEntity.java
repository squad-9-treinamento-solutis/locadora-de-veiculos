package br.com.solutis.locadora.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "car_rents")
public class CarRentEntity extends AbstractEntity {
    @NotNull(message = "Rent date is required")
    @Column(name = "rent_date", nullable = false)
    private Date rentDate;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @NotNull(message = "Value is required")
    @Column(nullable = false)
    private BigDecimal value;

    @JsonIgnoreProperties("carRents")
    @OneToOne(optional = false)
    @JoinColumn(name = "insurance_policy_id", nullable = false)
    private InsurancePolicyEntity insurancePolicy;

    @JsonIgnoreProperties("carRents")
    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @JsonIgnoreProperties("carRents")
    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;
}
