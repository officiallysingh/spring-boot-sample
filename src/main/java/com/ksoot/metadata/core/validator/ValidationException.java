package com.ksoot.metadata.core.validator;

import com.ksoot.metadata.core.util.MessageResolvers;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.context.MessageSourceResolvable;

/**
 * Exception used to notify validation errors using {@link Validators#validate(Object)}.
 *
 * <p>This exception is {@link Localizable}, providing optional message code and arguments for
 * validation message localization.
 *
 * <p>ValidationException may act as a wrapper for multiple validation errors, accessible through
 * {@link #getCauses()} method.
 */
public class ValidationException extends RuntimeException {

  private static final long serialVersionUID = -6564869827469114206L;

  /** Localizable message (may be null) */
  private final transient MessageSourceResolvable message;

  /** ValidationExceptions that caused this exception */
  private final Collection<ValidationException> causes;

  /**
   * Constructor with message
   *
   * @param message Validation error message
   */
  public ValidationException(String message) {
    this(MessageResolvers.builder().defaultMessage(message).build(), Collections.emptySet());
  }

  public ValidationException(String message, String... codes) {
    this(
        MessageResolvers.builder().defaultMessage(message).codes(codes).build(),
        Collections.emptySet());
  }

  /**
   * Constructor with localized message
   *
   * @param message Default validation error message
   * @param messageCode Validation error message code
   * @param messageArguments Optional message localization arguments
   */
  public ValidationException(String message, String messageCode, Object... messageArguments) {
    this(
        MessageResolvers.builder()
            .defaultMessage(message)
            .codes(messageCode)
            .arguments(messageArguments)
            .build(),
        Collections.emptySet());
  }

  public ValidationException(String message, String[] messageCode, Object... messageArguments) {
    this(
        MessageResolvers.builder()
            .defaultMessage(message)
            .codes(messageCode)
            .arguments(messageArguments)
            .build(),
        Collections.emptySet());
  }

  /**
   * Constructor with {@link Localizable} message
   *
   * @param message Validation error message
   */
  public ValidationException(MessageSourceResolvable message) {
    this(message, Collections.emptySet());
  }

  /**
   * Constructor with causes.
   *
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(ValidationException... causes) {
    this((MessageSourceResolvable) null, Arrays.asList(causes));
  }

  /**
   * Constructor with causes.
   *
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(Collection<ValidationException> causes) {
    this((MessageSourceResolvable) null, causes);
  }

  /**
   * Constructor with message and causes.
   *
   * @param message The validation error message
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(String message, ValidationException... causes) {
    this(MessageResolvers.builder().defaultMessage(message).build(), causes);
  }

  /**
   * Constructor with message and causes.
   *
   * @param message The validation error message
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(String message, Collection<ValidationException> causes) {
    this(MessageResolvers.builder().defaultMessage(message).build(), causes);
  }

  /**
   * Constructor with message and causes.
   *
   * @param message The validation error message
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(MessageSourceResolvable message, ValidationException... causes) {
    this(message, Arrays.asList(causes));
  }

  /**
   * Constructor with message and causes.
   *
   * @param message The validation error message
   * @param causes One or more {@link ValidationException}s that caused this exception
   */
  public ValidationException(
      MessageSourceResolvable message, Collection<ValidationException> causes) {
    super((message != null) ? message.getDefaultMessage() : null);
    this.message = message;
    this.causes = (causes != null) ? causes : Collections.emptySet();
  }

  /**
   * Get the exception localizable message, if available.
   *
   * @return Optional exception localizable message
   */
  public Optional<MessageSourceResolvable> getLocalizableMessage() {
    return Optional.ofNullable(message);
  }

  /**
   * Get the {@link ValidationException}s that caused this exception.
   *
   * @return A collection of causes, empty if none
   */
  public Collection<ValidationException> getCauses() {
    return causes;
  }

  /**
   * Get all the validation error messages carried by this validation exception.
   *
   * @return A list of validation error messages which correspond to inner validation exceptions, if
   *     any, or a list with only one element which corresponds to the validation exception itself.
   */
  public List<MessageSourceResolvable> getValidationMessages() {
    if (message != null) {
      return Collections.singletonList(message);
    }
    return getCauses().stream().map(cause -> cause.message).toList();
  }

  /**
   * Get all the validation error messages of the validation causes, if any.
   *
   * @return A list of validation error messages which correspond to inner validation exceptions, if
   *     any
   */
  public List<MessageSourceResolvable> getCausesMessages() {
    return getCauses().stream().map(cause -> cause.message).toList();
  }
}
