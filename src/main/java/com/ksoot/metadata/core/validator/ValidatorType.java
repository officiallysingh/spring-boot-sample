package com.ksoot.metadata.core.validator;

public enum ValidatorType {
  REQUIRED,
  MIN,
  MAX,
  EXCLUSIVE_MIN,
  EXCLUSIVE_MAX,
  PATTERN,
  INTEGER_DIGITS,
  FRACTION_DIGITS,
  PAST,
  FUTURE,
  EMAIL,
  IN,
  NOT_IN;
}
