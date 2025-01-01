package com.ksoot.metadata.core.validator;

import java.io.Serializable;

/**
 * Validate a value to check if it is valid.
 *
 * <p>For {@link Validatable} classes, {@link #validate(Object)} method can be used to check if a
 * value is valid. An {@link ValidationException} with an appropriate validation error message is
 * thrown if the value is not valid.
 *
 * <p>{@link ValidatorSupport} interface should be implemented by classes which declare support for
 * {@link Validator}s.
 *
 * <p>This interface provides several static methods to obtain builtin Validators for common use
 * cases.
 *
 * @param <T> Validation target value type
 * @since 5.0.0
 */
@FunctionalInterface
public interface Validator<T> extends Serializable {

  /**
   * Validate given <code>value</code>. If the value is not valid, an {@link ValidationException} is
   * thrown.
   *
   * <p>The {@link ValidationException} is {@link Localizable}, providing optional message code and
   * arguments for validation message localization.
   *
   * @param value The value to validate (may be null)
   * @throws ValidationException If the value is not valid, providing the validation error message.
   */
  void validate(T value) throws ValidationException;
}
