package br.com.solutis.locadora.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "car_accessories")
public class CarAccessory extends AbstractEntity {
    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "car_accessories_cars",
            joinColumns = @JoinColumn(name = "car_accessory_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;
}
