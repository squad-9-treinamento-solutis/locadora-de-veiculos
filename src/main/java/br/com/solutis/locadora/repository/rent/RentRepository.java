package br.com.solutis.locadora.repository.rent;

import br.com.solutis.locadora.model.entity.rent.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

}
