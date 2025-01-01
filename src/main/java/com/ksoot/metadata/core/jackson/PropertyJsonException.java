package com.ksoot.metadata.core.jackson;

public class PropertyJsonException extends RuntimeException {

  private static final long serialVersionUID = 4255245974256602343L;

  /**
   * Constructor with error message.
   *
   * @param message Error message
   */
  public PropertyJsonException(String message) {
    super(message);
  }

  /**
   * Constructor with error message and cause.
   *
   * @param message Error message
   * @param cause Cause
   */
  public PropertyJsonException(String message, Throwable cause) {
    super(message, cause);
  }
}
