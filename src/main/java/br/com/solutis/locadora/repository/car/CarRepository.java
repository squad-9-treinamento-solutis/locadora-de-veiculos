package br.com.solutis.locadora.repository.car;

import br.com.solutis.locadora.model.entity.car.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByDeletedFalseAndRentedFalse(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Car c " +
            "LEFT JOIN c.rents r " +
            "WHERE c.rented = false AND " +
            "(r.endDate < :startDate OR r.startDate > :endDate OR r IS NULL)")
    List<Car> findAvailableCarsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
