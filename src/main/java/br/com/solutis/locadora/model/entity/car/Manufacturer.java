package br.com.solutis.locadora.model.entity.car;

import br.com.solutis.locadora.model.entity.AbstractEntity;
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
@Table(name = "manufacturers")
public class Manufacturer extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties("manufacturer")
    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Model> models;
}
