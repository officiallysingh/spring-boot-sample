package com.omniterasoft.springboot.sample;

import java.util.UUID;

public class TestConstants {
  public static final String FORWARD_SLASH = "/";
  public static final String HEADER_LOCATION = "Location";
  public static final UUID TEST_OBJECT_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
  public static final String JSON_PATH_ERROR_CODE = "$.errorCode";
  public static final String JSON_PATH_ERROR_MESSAGE = "$.message";
  public static final String SHOULD_THROW_SERVICE_EXCEPTION_MESSAGE =
      "Should throw 'ServiceException'";
  public static final String EXPECTED_ERROR_CODE_MESSAGE_TEMPLATE =
      "'ServiceException' Error code should be '%s'";
  public static final String ERROR_CODE_BAD_REQUEST = "400";
  public static final int THREE = 3;
}