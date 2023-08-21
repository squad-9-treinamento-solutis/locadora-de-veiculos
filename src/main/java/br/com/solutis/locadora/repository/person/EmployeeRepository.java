package br.com.solutis.locadora.repository.person;

import br.com.solutis.locadora.model.entity.person.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDeletedFalse(Pageable pageable);
}
