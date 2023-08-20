package br.com.solutis.locadora.model.entity.car;

import br.com.solutis.locadora.model.entity.rent.Rent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plate;

    @Column(unique = true, nullable = false)
    private String chassis;

    @Column(nullable = false)
    private String color;

    @Column(name = "daily_value", nullable = false)
    private BigDecimal dailyValue;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean rented = false;

    @JsonIgnoreProperties("cars")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @JsonIgnoreProperties("cars")
    @ManyToMany
    @JoinTable(
            name = "car_accessories",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private List<Accessory> accessories;

    @JsonIgnoreProperties("cars")
    @OneToMany(mappedBy = "car")

    private List<Rent> rents;
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;


    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
