package br.com.solutis.locadora.repository.car;

import br.com.solutis.locadora.model.entity.car.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Page<Model> findByDeletedFalse(Pageable pageable);
}
