package br.com.solutis.locadora.model.entity.rent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "insurance_policies")
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "franchise_value", nullable = false)
    private BigDecimal franchiseValue;

    @Column(name = "third_party_coverage")
    private boolean thirdPartyCoverage = false;

    @Column(name = "natural_phenomena_coverage")
    private boolean naturalPhenomenaCoverage = false;

    @Column(name = "theft_coverage")
    private boolean theftCoverage = false;

    @JsonIgnoreProperties("insurancePolicy")
    @OneToMany(mappedBy = "insurancePolicy")
    private List<Rent> carRents;

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

    @Override
    public String toString() {
        return "InsurancePolicy{" +
                "id=" + id +
                ", franchiseValue=" + franchiseValue +
                ", thirdPartyCoverage=" + thirdPartyCoverage +
                ", naturalPhenomenaCoverage=" + naturalPhenomenaCoverage +
                ", theftCoverage=" + theftCoverage +
                ", deleted=" + deleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
