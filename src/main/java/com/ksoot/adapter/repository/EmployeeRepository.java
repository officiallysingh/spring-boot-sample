package com.ksoot.adapter.repository;

import com.ksoot.domain.model.Employee;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

  Optional<Employee> findByCode(final String code);

  boolean existsByCode(final String code);
}
