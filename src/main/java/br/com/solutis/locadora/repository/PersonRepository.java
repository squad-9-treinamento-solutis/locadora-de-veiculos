package br.com.solutis.locadora.repository;

import br.com.solutis.locadora.model.entity.PersonEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GenericRepository<PersonEntity> {
}
