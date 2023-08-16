package br.com.solutis.locadora.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "employees")
public class EmployeeEntity extends AbstractEntity {
    @NotNull(message = "Registration is required")
    @NotBlank(message = "Registration is required")
    @Size( max = 255, message = "Registration must be less than 255 characters long")
    @Column(unique = true, nullable = false)
    private String registration;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private PersonEntity person;
}
