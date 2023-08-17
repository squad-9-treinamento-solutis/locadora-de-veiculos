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
@Table(name = "car_models")
public class CarModel extends AbstractEntity {
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CarModelCategoryEnum category;

    @JsonIgnoreProperties("models")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private CarManufacturer manufacturer;

    @JsonIgnoreProperties("models")
    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Car> cars;
}
