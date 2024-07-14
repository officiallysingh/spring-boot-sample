package com.omniterasoft.springboot.sample.domain.service;

import static com.omniterasoft.springboot.sample.TestConstants.SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE;
import static com.omniterasoft.springboot.sample.TestConstants.TEST_OBJECT_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ksoot.problem.core.ApplicationProblem;
import com.ksoot.problem.spring.config.ProblemBeanRegistry;
import com.ksoot.problem.spring.config.ProblemMessageProviderConfig;
import com.omniterasoft.springboot.sample.WebTestConfiguration;
import com.omniterasoft.springboot.sample.adapter.repository.EmployeeRepository;
import com.omniterasoft.springboot.sample.domain.model.Employee;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeCreationRQ;
import com.omniterasoft.springboot.sample.domain.model.dto.EmployeeUpdationRQ;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// @com.ksoot.common.spring.boot.annotation.Test
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {WebTestConfiguration.class})
@ContextConfiguration(
    classes = {
      MessageSourceAutoConfiguration.class,
      ProblemBeanRegistry.class,
      ProblemMessageProviderConfig.class
    })
@TestPropertySource(
    locations = {
      "classpath:i18n/messages.properties",
      "classpath:i18n/errors.properties",
      "classpath:i18n/problems.properties"
    })
@TestPropertySource(properties = "detail.not.found=Requested resource not found")
public class EmployeeServiceTest {

  private static final String TEST_EMPLOYEE_CODE = "ABC123XYZ";
  private static final String EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE =
      "EmployeeResponse should not be null";
  private static final String EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE =
      "EmployeeResponse's '%s' should be '%s'";
  private static final String CODE = "code";

  @Mock private EmployeeRepository employeeRepository;

  // @Autowired
  // private MessageSource messageSource;

  @InjectMocks private EmployeeService employeeService;

  private EmployeeCreationRQ newEmployeeCreateRequest() {
    return EmployeeCreationRQ.builder()
        .code(TEST_EMPLOYEE_CODE)
        .name("Amit Dahiya")
        .dob(LocalDate.of(1980, 8, 17))
        .build();
  }

  private Employee newState(final EmployeeCreationRQ request) {
    return Employee.builder().code(request.code()).name(request.name()).build();
  }

  private Employee newState() {
    return Employee.builder()
        .code(TEST_EMPLOYEE_CODE)
        .name("Amit Dahiya")
        .dob(LocalDate.of(1980, 8, 17))
        .build();
  }

  @Test
  @DisplayName("Test Create State successfully")
  public void testCreateEmployee_Success() {
    final EmployeeCreationRQ request = this.newEmployeeCreateRequest();

    final Employee employee = this.newState(request);
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    final Employee employeeResponse = this.employeeService.createEmployee(request);

    assertAll(
        "Verify Create State response",
        //        () -> assertNotNull(employeeResponse,
        // EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                employeeResponse.getCode(),
                request.code(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, CODE, request.code())));
  }

  @Test
  @DisplayName("Test Get State by Id or Code successfully")
  public void testGetState_ByIdOrCode_Success() {
    final Employee employee = this.newState();
    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.of(employee));

    final Employee employeeResponse = this.employeeService.getEmployeeById(TEST_OBJECT_ID);

    assertAll(
        "Verify Get State by Id or Code response",
        () -> assertNotNull(employeeResponse, EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                employeeResponse.getCode(),
                TEST_EMPLOYEE_CODE,
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, CODE, TEST_EMPLOYEE_CODE)));
  }

  //  @Disabled
  @Test
  @DisplayName("Test Get State by Id or Code failure due to non existent record")
  public void testGetState_ById_NotFound_Failure() throws Exception {

    //    when(this.messageSource.getMessage(any(MessageSourceResolvable.class),
    // any(Locale.class))).thenReturn("Some message");
    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.empty());

    final ApplicationProblem exception =
        assertThrows(
            ApplicationProblem.class,
            () -> this.employeeService.getEmployeeById(TEST_OBJECT_ID),
            SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE);
  }

  @Test
  @DisplayName("Test validate State for given code success")
  public void testValidateState_ByCode_Success() {
    when(this.employeeRepository.existsByCode(TEST_EMPLOYEE_CODE)).thenReturn(true);
    assertTrue(
        this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE),
        "State should exist with code: " + TEST_EMPLOYEE_CODE);
  }

  @Test
  @DisplayName("Test validate State for given code failure")
  public void testValidateState_ByCode_Failure() {
    when(this.employeeRepository.existsByCode(TEST_EMPLOYEE_CODE)).thenReturn(false);
    assertFalse(
        this.employeeService.doesEmployeeExist(TEST_EMPLOYEE_CODE),
        "State should not exist with code: " + TEST_EMPLOYEE_CODE);
  }

  @Test
  @DisplayName("Test update State successfully")
  public void testUpdateEmployee_Success() throws Exception {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder()
            .name("Another Name")
            .dob(LocalDate.now().minusYears(20))
            .build();

    final Employee existingEmployee = this.newState();
    when(this.employeeRepository.findById(TEST_OBJECT_ID))
        .thenReturn(Optional.of(existingEmployee));

    final Employee updatedEmployee =
        Employee.builder().code(TEST_EMPLOYEE_CODE).name(request.name()).dob(request.dob()).build();
    when(this.employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

    final Employee employeeResponse = this.employeeService.updateEmployee(TEST_OBJECT_ID, request);
    this.assertUpdateEmployeeResponse(request, employeeResponse);
  }

  private void assertUpdateEmployeeResponse(
      final EmployeeUpdationRQ request, final Employee response) {
    assertAll(
        "Verify Update State response",
        () -> assertNotNull(response, EMPLOYEE_RESPONSE_SHOULD_NOT_BE_NULL_MESSAGE),
        () ->
            assertEquals(
                response.getName(),
                request.name(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, "name", request.name())),
        () ->
            assertEquals(
                response.getDob(),
                request.dob(),
                String.format(
                    EXPECTED_EMPLOYEE_RESPONSE_CODE_MESSAGE_TEMPLATE, "dob", request.dob())));
  }

  //  @Disabled
  @Test
  @DisplayName("Test update State failure for non existent State record with given Id")
  public void testUpdateEmployee_NotFound_Failure() throws Exception {
    final EmployeeUpdationRQ request =
        EmployeeUpdationRQ.builder().name("Yet another name").build();

    when(this.employeeRepository.findById(TEST_OBJECT_ID)).thenReturn(Optional.empty());
    final ApplicationProblem exception =
        assertThrows(
            ApplicationProblem.class,
            () -> this.employeeService.updateEmployee(TEST_OBJECT_ID, request),
            SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE);
    assertAll(
        "Verify Exception attributes",
        () -> assertNotNull(exception, "exception should not be null"),
        () ->
            assertEquals(
                exception.getStatus(),
                HttpStatus.NOT_FOUND,
                "Expected exception HttpStatus is " + HttpStatus.NOT_FOUND));
  }

  @Test
  @DisplayName("Test get all States successfully")
  public void testGetAllEmployees_Success() {
    final Employee employee = this.newState();
    when(this.employeeRepository.findAll()).thenReturn(List.of(employee, employee));
    final List<Employee> response = this.employeeService.getAllEmployees();

    assertAll(
        "Verify Get All States response",
        () -> assertNotNull(response, "Get all states response should not be null"),
        () -> assertEquals(response.size(), 2, "Expected number of records is 2"));
  }
}
