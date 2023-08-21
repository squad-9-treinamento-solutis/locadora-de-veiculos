package br.com.solutis.locadora.repository.car;

import br.com.solutis.locadora.model.entity.car.ModelCategoryEnum;
import br.com.solutis.locadora.model.entity.car.Accessory;
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
            "WHERE (:rented IS NULL OR c.rented = :rented) " +
            "AND (:accessory IS NULL OR :accessory MEMBER OF c.accessories) " +
            "AND (c.deleted = false) " +
            "AND (:category IS NULL OR c.model.category = :category) " +
            "AND ((:rented = true) OR (c.rented = false))"+
            "AND (:model IS NULL OR c.model.description = :model)")

    List<Car> findCarsByFilters(
            @Param("category") ModelCategoryEnum category,
            @Param("accessory") Accessory accessory,
            @Param("model") String model,
            @Param("rented") Boolean rented);
}
