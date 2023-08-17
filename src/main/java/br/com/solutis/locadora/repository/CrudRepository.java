package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
