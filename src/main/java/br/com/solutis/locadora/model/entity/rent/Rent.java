package br.com.solutis.locadora.model.entity.rent;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import br.com.solutis.locadora.model.entity.car.Car;
import br.com.solutis.locadora.model.entity.person.Driver;
import br.com.solutis.locadora.model.entity.person.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "rents")
public class Rent extends AbstractEntity {
    @Column(name = "rent_date", nullable = false)
    private Date rentDate;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private BigDecimal value;

    @JsonIgnoreProperties("carRents")
    @OneToOne(optional = false)
    @JoinColumn(name = "insurance_policy_id", nullable = false)
    private InsurancePolicy insurancePolicy;

    @JsonIgnoreProperties("carRents")
    @ManyToOne(optional = false)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @JsonIgnoreProperties("carRents")
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @JsonIgnoreProperties("carRents")
    @ManyToOne(optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
