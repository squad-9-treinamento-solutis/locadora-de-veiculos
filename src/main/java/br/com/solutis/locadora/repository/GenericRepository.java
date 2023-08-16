package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
