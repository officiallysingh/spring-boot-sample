package com.omniterasoft.springboot.sample.domain;

import com.ksoot.problem.core.ErrorType;
import com.omniterasoft.springboot.sample.common.CommonConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MasterErrorTypes implements ErrorType {
  INVALID_CITY_EXPAND_HEADER(
      "invalid.city.expand.header",
      "Invalid Header "
          + CommonConstants.HEADER_EXPAND
          + " value: {0}, allowed values are 'state' and 'areas'",
      HttpStatus.BAD_REQUEST),
  INVALID_STATE_EXPAND_HEADER(
      "invalid.state.expand.header",
      "Invalid Header " + CommonConstants.HEADER_EXPAND + " value: {0}, allowed value is 'cities'",
      HttpStatus.BAD_REQUEST),
  INVALID_AREA_EXPAND_HEADER(
      "invalid.ares.expand.header",
      "Invalid Header " + CommonConstants.HEADER_EXPAND + " value: {0}, allowed value is 'city'",
      HttpStatus.BAD_REQUEST);

  private final String errorKey;

  private final String defaultDetail;

  private final HttpStatus status;

  MasterErrorTypes(final String errorKey, final String defaultDetail, final HttpStatus status) {
    this.errorKey = errorKey;
    this.defaultDetail = defaultDetail;
    this.status = status;
  }
}
