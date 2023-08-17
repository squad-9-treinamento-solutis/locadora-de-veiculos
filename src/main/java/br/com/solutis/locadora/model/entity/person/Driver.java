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
@Table(name = "drivers")
public class Driver extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String cnh;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @JsonIgnoreProperties("drivers")
    @OneToMany(mappedBy = "driver")
    private List<Rent> rents;
}
