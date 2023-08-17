package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.CarEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends GenericRepository<CarEntity> {
}
