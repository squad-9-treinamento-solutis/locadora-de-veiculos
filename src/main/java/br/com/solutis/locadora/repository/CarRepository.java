package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.car.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car> {
    Page<Car> findByRented(boolean rented, Pageable pageable);

    @Query("SELECT DISTINCT c FROM Car c " +
            "LEFT JOIN c.rents r " +
            "WHERE c.rented = false AND " +
            "(r.endDate < :startDate OR r.startDate > :endDate OR r IS NULL)")
    List<Car> findAvailableCarsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
