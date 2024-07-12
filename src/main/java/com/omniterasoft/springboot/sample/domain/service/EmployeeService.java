package com.omniterasoft.springboot.sample.domain.service;

import com.ksoot.problem.core.Problems;
import com.omniterasoft.springboot.sample.adapter.repository.EmployeeRepository;
import com.omniterasoft.springboot.sample.domain.model.Employee;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeCreationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeUpdationRQ;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public Boolean doesEmployeeExist(final String code) {
    return this.employeeRepository.existsByCode(code);
  }

  public Employee createEmployee(final EmployeeCreationRQ request) {
    final Employee employee = Employee.builder().code(request.code()).name(request.name()).build();
    return this.employeeRepository.save(employee);
  }

  public Employee getEmployeeById(final UUID id) {
    return this.employeeRepository.findById(id).orElseThrow(Problems::notFound);
  }

  public List<Employee> getAllEmployees() {
    return this.employeeRepository.findAll();
  }

  public Employee updateEmployee(final UUID id, final EmployeeUpdationRQ request) {
    final Employee employee = this.employeeRepository.findById(id).orElseThrow(Problems::notFound);

    Optional.ofNullable(request.name()).ifPresent(employee::setName);

    return this.employeeRepository.save(employee);
  }

  public void deleteEmployee(final UUID id) {
    if (!this.employeeRepository.existsById(id)) {
      throw Problems.notFound();
    }
    this.employeeRepository.deleteById(id);
  }
}
