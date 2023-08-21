package br.com.solutis.locadora.model.entity.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "employees")
public class Employee extends Person {
    @Column(unique = true, nullable = false)
    private String registration;

    @Override
    public String toString() {
        return "Employee{" +
                "registration='" + registration + '\'' +
                "} " + super.toString();
    }
}
