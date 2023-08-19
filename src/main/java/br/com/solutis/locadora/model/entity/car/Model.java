package br.com.solutis.locadora.model.entity.car;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "models")
public class Model extends AbstractEntity {
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ModelCategoryEnum category;

    @JsonIgnoreProperties("models")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @JsonIgnoreProperties("models")
    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Car> cars;
}
