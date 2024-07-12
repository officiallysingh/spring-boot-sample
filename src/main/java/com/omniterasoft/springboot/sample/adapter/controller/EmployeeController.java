package com.omniterasoft.springboot.sample.adapter.controller;

import static com.omniterasoft.springboot.sample.common.CommonErrorKeys.EMPTY_UPDATE_REQUEST;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ksoot.problem.core.Problems;
import com.omniterasoft.springboot.sample.common.spring.rest.response.APIResponse;
import com.omniterasoft.springboot.sample.common.spring.util.GeneralMessageResolver;
import com.omniterasoft.springboot.sample.domain.mapper.SampleMappers;
import com.omniterasoft.springboot.sample.domain.model.Employee;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeCreationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeUpdationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeVM;
import com.omniterasoft.springboot.sample.domain.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class EmployeeController implements EmployeeApi {

  private final EmployeeService employeeService;

  @Override
  public ResponseEntity<Boolean> validateEmployeeCode(final String code) {
    return ResponseEntity.ok(this.employeeService.doesEmployeeExist(code));
  }

  @Override
  public ResponseEntity<APIResponse<?>> createEmployee(final EmployeeCreationRQ request) {
    final UUID id = this.employeeService.createEmployee(request).getId();
    return ResponseEntity.created(
            linkTo(methodOn(EmployeeController.class).getEmployee(id)).withSelfRel().toUri())
        .body(APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_CREATED));
  }

  @Override
  public ResponseEntity<EmployeeVM> getEmployee(final UUID id) {
    return ResponseEntity.ok(
        SampleMappers.INSTANCE.toEmployeeVM(this.employeeService.getEmployeeById(id)));
  }

  @Override
  public ResponseEntity<APIResponse<?>> updateEmployee(
      final UUID id, final EmployeeUpdationRQ request) {
    if (request.isEmpty()) {
      throw Problems.newInstance(EMPTY_UPDATE_REQUEST).throwAble(HttpStatus.BAD_REQUEST);
    }
    final Employee employee = this.employeeService.updateEmployee(id, request);
    return ResponseEntity.ok()
        .location(
            linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId()))
                .withSelfRel()
                .toUri())
        .body(APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_UPDATED));
  }

  @Override
  public ResponseEntity<List<EmployeeVM>> getAllEmployees() {
    return ResponseEntity.ok(
        this.employeeService.getAllEmployees().stream()
            .map(SampleMappers.INSTANCE::toEmployeeVM)
            .toList());
  }

  @Override
  public ResponseEntity<APIResponse<?>> deleteEmployee(final UUID id) {
    this.employeeService.deleteEmployee(id);
    return ResponseEntity.ok(
        APIResponse.newInstance().addMessage(GeneralMessageResolver.RECORD_DELETED));
  }
}
