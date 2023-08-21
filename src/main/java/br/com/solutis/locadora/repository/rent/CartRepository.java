package br.com.solutis.locadora.repository.rent;

import br.com.solutis.locadora.model.entity.rent.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findByDeletedFalse(Pageable pageable);

    Cart findByDriverId(Long driverId);
}
