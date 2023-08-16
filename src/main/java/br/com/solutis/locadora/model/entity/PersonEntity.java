package br.com.solutis.locadora.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Table(name = "persons")
public class PersonEntity extends AbstractEntity {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters long")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "CPF is required")
    @NotBlank(message = "CPF is required")
    @Size(min = 14, max = 14, message = "CPF must be 14 characters long")
    @Pattern(regexp = "^(?!(\\d)\\1{10})\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF must be in the format xxx.xxx.xxx-xx")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "Birth Date is required")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private java.util.Date birthDate;

    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private DriverEntity driver;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private EmployeeEntity employee;
}
