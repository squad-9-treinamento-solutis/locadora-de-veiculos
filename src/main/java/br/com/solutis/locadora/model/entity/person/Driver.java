package br.com.solutis.locadora.model.entity.person;

import br.com.solutis.locadora.model.entity.cart.Cart;
import br.com.solutis.locadora.model.entity.rent.Rent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "drivers")
public class Driver extends Person {
    @Column(unique = true, nullable = false)
    private String cnh;

    @JsonIgnoreProperties("drivers")
    @OneToMany(mappedBy = "driver")
    private List<Rent> rents;

    @JsonIgnoreProperties("drivers")
    @OneToOne(mappedBy = "driver")
    private Cart cart;
}
