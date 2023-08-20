package br.com.solutis.locadora.repository.person;

import br.com.solutis.locadora.model.entity.person.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Page<Driver> findByDeletedFalse(Pageable pageable);
}
