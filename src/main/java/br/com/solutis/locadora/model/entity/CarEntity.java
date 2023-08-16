package br.com.solutis.locadora.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Table(name = "cars")
public class CarEntity extends AbstractEntity {
    @NotNull(message = "Plate is required")
    @NotBlank(message = "Plate is required")
    @Size(min = 1, max = 255, message = "Plate must be between 1 and 255 characters long")
    @Column(unique = true, nullable = false)
    private String plate;

    @NotNull(message = "Chassis is required")
    @NotBlank(message = "Chassis is required")
    @Size(min = 1, max = 255, message = "Chassis must be between 1 and 255 characters long")
    @Column(unique = true, nullable = false)
    private String chassis;

    @NotNull(message = "Color is required")
    @NotBlank(message = "Color is required")
    @Column(nullable = false)
    private String color;

    @NotNull(message = "Daily value is required")
    @Column(name = "daily_value", nullable = false)
    private BigDecimal dailyValue;

    @NotNull(message = "Image URL is required")
    @NotBlank(message = "Image URL is required")
    @URL(message = "Image URL must be a valid URL")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean rented = false;

    @JsonIgnoreProperties("cars")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModelEntity model;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "car_accessories_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "car_accessory_id")
    )
    private List<CarAccessoryEntity> accessories;

    @JsonIgnoreProperties("cars")
    @OneToMany(mappedBy = "car")
    private List<CarRentEntity> carRents;
}
