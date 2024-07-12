package com.omniterasoft.springboot.sample.adapter.controller;

import static com.omniterasoft.springboot.sample.common.spring.rest.ApiConstants.*;
import static com.omniterasoft.springboot.sample.common.spring.rest.ApiStatus.*;

import com.omniterasoft.springboot.sample.common.spring.rest.Api;
import com.omniterasoft.springboot.sample.common.spring.rest.response.APIResponse;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeCreationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeUpdationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/employees")
// @Tag(name = "Employee", description = "management APIs")
public interface EmployeeApi extends Api {

  @Operation(
      operationId = "validate-employee-code",
      summary = "Validate Employee code. Check if a Employee with given code exists")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Returns true or false if Employee exists or not respectively")
      })
  @GetMapping(path = "/{code}/validate", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Boolean> validateEmployeeCode(
      @Parameter(description = "Employee Code", required = true) @PathVariable(name = "code")
          final String code);

  @Operation(operationId = "create-employee", summary = "Creates a Employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_201, description = "Employee created successfully"),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE)))
      })
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> createEmployee(
      @Parameter(description = "Create Employee request", required = true) @RequestBody @Valid
          final EmployeeCreationRQ request);

  @Operation(operationId = "get-employee-by-id", summary = "Gets a Employee by id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = SC_200, description = "Employee returned successfully"),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested Employee not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<EmployeeVM> getEmployee(
      @Parameter(
              description = "Employee Id",
              required = true,
              example = "550e8400-e29b-41d4-a716-446655440000")
          @PathVariable(name = "id")
          final UUID id);

  @Operation(operationId = "get-all-employees", summary = "Get all Employees")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description =
                "Employees list returned successfully. Returns an empty list if no records found"),
        @ApiResponse(responseCode = "500", description = "Internal Server error")
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<EmployeeVM>> getAllEmployees();

  @Operation(operationId = "update-employee", summary = "Updates an Employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
        @ApiResponse(
            responseCode = SC_400,
            description = "Bad request",
            content = @Content(examples = @ExampleObject(BAD_REQUEST_EXAMPLE_RESPONSE))),
        @ApiResponse(
            responseCode = "404",
            description = "State not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @PatchMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> updateEmployee(
      @Parameter(
              description = "Employee Id or Code",
              required = true,
              example = "550e8400-e29b-41d4-a716-446655440000")
          @PathVariable(name = "id")
          final UUID id,
      @Parameter(description = "Update Employee request", required = true) @RequestBody @Valid
          final EmployeeUpdationRQ request);

  @Operation(operationId = "delete-employee", summary = "Deletes an Employee")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = SC_200,
            description = "Employee deleted successfully",
            content = @Content(examples = @ExampleObject(RECORD_DELETED_RESPONSE))),
        @ApiResponse(
            responseCode = SC_404,
            description = "Requested Employee not found",
            content = @Content(examples = @ExampleObject(NOT_FOUND_EXAMPLE_RESPONSE)))
      })
  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<APIResponse<?>> deleteEmployee(
      @Parameter(
              description = "Employee Id",
              required = true,
              example = "550e8400-e29b-41d4-a716-446655440000")
          @PathVariable(name = "id")
          final UUID id);
}
