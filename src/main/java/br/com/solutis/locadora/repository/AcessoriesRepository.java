package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.car.Accessory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcessoriesRepository extends JpaRepository<Accessory, Long> {
    Page<Accessory> findByDeletedFalse(Pageable pageable);
}