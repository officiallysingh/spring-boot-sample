package com.gangatourism.ets.util;

public class Constants {

  public static final String REGEX_EMPLOYEE_CODE = "^[A-Z0-9]{5,10}$";

  public static final String REGEX_ALPHABETS = "^[a-zA-Z]*$";

  public static final String REGEX_ALPHABETS_AND_SPACES = "^[a-zA-Z ]+$";

  private Constants() {
    throw new IllegalStateException(
        "Just a constants container class, not supposed to be instantiated");
  }
}
