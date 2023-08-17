package br.com.solutis.locadora.model.entity;

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
@Table(name = "car_manufacturers")
public class CarManufacturer extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("manufacturer")
    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CarModel> models;
}
