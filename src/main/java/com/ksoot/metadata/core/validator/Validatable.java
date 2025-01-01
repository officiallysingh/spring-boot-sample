package com.ksoot.metadata.core.validator;

import java.util.Collection;
import java.util.List;

/**
 * Declares the support for value validation using {@link Validator}s.
 *
 * @param <T> Supported validators type
 */
public interface Validatable<T> {

  /**
   * Adds a validator.
   *
   * @param validator The validator to add (not null)
   */
  Validatable<T> addValidator(Validator<T> validator);

  Validatable<T> addValidators(List<Validator<T>> validators);

  /**
   * Removes given <code>validator</code>, if it was registered.
   *
   * @param validator The validator to remove
   */
  Validatable<T> removeValidator(Validator<T> validator);

  /**
   * Get the registered validators.
   *
   * @return Registered validator, or an empty collection if none
   */
  Collection<Validator<T>> getValidators();

  /**
   * Checks the validity of the given <code>value</code> against every registered validator, if any.
   * If the value is not valid, an {@link ValidationException} is thrown.
   *
   * <p>The {@link ValidationException} is {@link Localizable}, providing optional message code and
   * arguments for validation message localization.
   *
   * @param value Value to validate
   * @throws ValidationException If the value is not valid, providing the validation error message.
   */
  void validate() throws ValidationException;
}
