package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.car.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car> {
    Page<Car> findByRented(boolean rented, Pageable pageable);
}
