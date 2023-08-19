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
@Table(name = "manufacturers")
public class Manufacturer extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnoreProperties("manufacturer")
    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Model> models;
}
