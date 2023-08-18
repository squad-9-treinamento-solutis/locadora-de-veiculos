package br.com.solutis.locadora.model.entity.person;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import br.com.solutis.locadora.model.entity.rent.Rent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "employee")
public class Employee extends AbstractEntity {
    @Column(unique = true, nullable = false)
        private String Registration;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @JsonIgnoreProperties("employees")
    @OneToMany(mappedBy = "employee")
    private List<Rent> rents;
}
