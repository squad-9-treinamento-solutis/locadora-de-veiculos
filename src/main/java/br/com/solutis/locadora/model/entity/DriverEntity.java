package br.com.solutis.locadora.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "drivers")
public class DriverEntity extends PersonEntity {
    @NotNull(message = "CNH is required")
    @NotBlank(message = "CNH is required")
    @Size(max = 255, message = "CNH must be less than 255 characters long")
    @Column(unique = true, nullable = false)
    private String cnh;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity person;

    @JsonIgnoreProperties("drivers")
    @OneToMany(mappedBy = "driver")
    private List<CarRentEntity> carRents;
}
