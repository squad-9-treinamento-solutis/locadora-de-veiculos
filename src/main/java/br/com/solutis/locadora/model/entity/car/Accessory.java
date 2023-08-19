package br.com.solutis.locadora.model.entity.car;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "accessories")
public class Accessory extends AbstractEntity {
    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "car_accessories",
            joinColumns = @JoinColumn(name = "accessory_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;
}
