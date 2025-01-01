/*
 * Copyright 2016-2017 Axioma srl.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ksoot.metadata.core.validator;

import com.ksoot.metadata.core.util.CalendarUtils;
import com.ksoot.metadata.core.util.FormatUtils;
import com.ksoot.metadata.core.util.MessageResolvers;
import com.ksoot.metadata.core.util.TypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.Assert;

/**
 * Validate a value to check if it is valid.
 *
 * <p>For {@link Validatable} classes, {@link #validate(Object)} method can be used to check if a
 * value is valid. An {@link ValidationException} with an appropriate validation error message is
 * thrown if the value is not valid.
 *
 * <p>{@link ValidatorSupport} interface should be implemented by classes which declare support for
 * {@link Validators}s.
 *
 * <p>This interface provides several static methods to obtain builtin Validators for common use
 * cases.
 *
 * @param <T> Validation target value type
 * @since 5.0.0
 */
public interface Validators<T> extends Serializable {

  // Builders

  /**
   * Create a {@link Validators} which uses given {@link Predicate} to perform value validation and
   * the given localizable <code>message</code> as invalid value message when the predicate
   * condition is not satisfied.
   *
   * @param <T> Value type
   * @param predicate Predicate to use to check if value is valid (not null)
   * @param message Invalid value message (not null)
   * @return A new {@link Validators} using given {@link Predicate} and invalid value message
   */
  static <T> Validator<T> create(Predicate<T> predicate, MessageSourceResolvable message) {
    return new DefaultValidator<>(predicate, message);
  }

  /**
   * Create a {@link Validators} which uses given {@link Predicate} to perform value validation and
   * the given localizable <code>message</code> as invalid value message when the predicate
   * condition is not satisfied.
   *
   * @param <T> Value type
   * @param predicate Predicate to use to check if value is valid (not null)
   * @param message Invalid value message
   * @param messageCode Invalid value message localization code
   * @param messageAguments Optional message localization arguments
   * @return A new {@link Validators} using given {@link Predicate} and invalid value message
   */
  static <T> Validator<T> create(
      Predicate<T> predicate, String message, String messageCode, Object... messageAguments) {
    return new DefaultValidator<>(
        predicate,
        MessageResolvers.builder()
            .defaultMessage(message)
            .codes(messageCode)
            .arguments(messageAguments)
            .build());
  }

  /**
   * Create a {@link Validators} which uses given {@link Predicate} to perform value validation and
   * the given localizable <code>message</code> as invalid value message when the predicate
   * condition is not satisfied.
   *
   * @param <T> Value type
   * @param predicate Predicate to use to check if value is valid (not null)
   * @param message Invalid value message
   * @return A new {@link Validators} using given {@link Predicate} and invalid value message
   */
  static <T> Validator<T> create(Predicate<T> predicate, String message) {
    return new DefaultValidator<>(
        predicate, MessageResolvers.builder().defaultMessage(message).build());
  }

  // ------- Builtin validators

  // Null

  /**
   * Build a validator that checks that given value is <code>null</code> and uses default {@link
   * ValidationMessage#NULL} localizable message as validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @return Validator
   */
  static <T> Validator<T> isNull() {
    return isNull(ValidationMessage.NULL);
  }

  /**
   * Build a validator that checks that given value is <code>null</code> and uses given {@link
   * Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T> Validator<T> isNull(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return isNull(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is <code>null</code>.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T> Validator<T> isNull(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) throw new ValidationException(message, messageCode);
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.empty();
      }
    };
  }

  // Not null

  /**
   * Build a validator that checks that given value is not <code>null</code> and uses default {@link
   * ValidationMessage#NOT_NULL} localizable message as validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @return Validator
   */
  static <T> Validator<T> notNull() {
    return notNull(ValidationMessage.NOT_NULL);
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> and uses given {@link
   * Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T> Validator<T> notNull(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return notNull(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is not <code>null</code>.
   *
   * <p>Supported data types: all
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T> Validator<T> notNull(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v == null) throw new ValidationException(message, messageCode);
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().required().build());
      }
    };
  }

  // Not empty

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty, and uses
   * default {@link ValidationMessage#NOT_EMPTY} localizable message as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Collection}, {@link Map} and Arrays
   *
   * @param <T> Validator type
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> notEmpty() {
    return notEmpty(ValidationMessage.NOT_EMPTY);
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty, and uses
   * given {@link Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Collection}, {@link Map} and Arrays
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> notEmpty(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return notEmpty(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Collection}, {@link Map} and Arrays
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  @SuppressWarnings("serial")
  static <T> Validator<T> notEmpty(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v == null) {
          throw new ValidationException(message, messageCode);
        }
        if (!v.getClass().isArray()
            && !CharSequence.class.isAssignableFrom(v.getClass())
            && !Collection.class.isAssignableFrom(v.getClass())
            && !Map.class.isAssignableFrom(v.getClass())) {
          // unsupported type
          throw new UnsupportedValidationTypeException(
              "Data type not supported by noEmpty validator: " + v.getClass().getName());
        }
        if (CharSequence.class.isAssignableFrom(v.getClass()) && ((CharSequence) v).length() == 0)
          throw new ValidationException(message, messageCode);
        if (Collection.class.isAssignableFrom(v.getClass()) && ((Collection<?>) v).isEmpty())
          throw new ValidationException(message, messageCode);
        if (Map.class.isAssignableFrom(v.getClass()) && ((Map<?, ?>) v).isEmpty())
          throw new ValidationException(message, messageCode);
        if (v.getClass().isArray() && ((Object[]) v).length == 0)
          throw new ValidationException(message, messageCode);
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().required().build());
      }
    };
  }

  // Not Blank

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty, trimming
   * spaces, and uses default {@link ValidationMessage#NOT_EMPTY} localizable message as validation
   * error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> notBlank() {
    return notBlank(ValidationMessage.NOT_EMPTY);
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty, trimming
   * spaces, and uses given {@link Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> notBlank(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return notBlank(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> nor empty, trimming
   * spaces.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends CharSequence> Validator<T> notBlank(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v == null || v.toString().trim().length() == 0)
          throw new ValidationException(message, messageCode);
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().required().build());
      }
    };
  }

  // Max

  /**
   * Build a validator that checks that given value is lower than or equal to <code>max</code>
   * value, and uses {@link ValidationMessage#MAX} as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given max value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given max value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given max value
   *   <li>Collections, Maps and arrays: size/length is checked against given max value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param max Max value
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> max(double max) {
    return max(max, ValidationMessage.MAX);
  }

  /**
   * Build a validator that checks that given value is lower than or equal to <code>max</code>
   * value, and uses given {@link Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given max value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given max value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given max value
   *   <li>Collections, Maps and arrays: size/length is checked against given max value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param max Max value
   * @param message Validation error message
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> max(double max, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return max(max, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is lower than or equal to <code>max</code>
   * value.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given max value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given max value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given max value
   *   <li>Collections, Maps and arrays: size/length is checked against given max value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param max Max value
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  @SuppressWarnings("serial")
  static <T> Validator<T> max(double max, String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {

          if (!v.getClass().isArray()
              && !TypeUtils.isNumber(v.getClass())
              && !CharSequence.class.isAssignableFrom(v.getClass())
              && !Collection.class.isAssignableFrom(v.getClass())
              && !Map.class.isAssignableFrom(v.getClass())) {
            // unsupported type
            throw new UnsupportedValidationTypeException(
                "Data type not supported by max validator: " + v.getClass().getName());
          }

          if (TypeUtils.isNumber(v.getClass())) {
            if (TypeUtils.isDecimalNumber(v.getClass())) {
              if (((Number) v).doubleValue() > max) {
                throw new ValidationException(message, messageCode, max);
              }
            } else {
              if (((Number) v).longValue() > (long) max) {
                throw new ValidationException(message, messageCode, (long) max);
              }
            }
          }
          if (CharSequence.class.isAssignableFrom(v.getClass())
              && ((CharSequence) v).length() > (int) max)
            throw new ValidationException(message, messageCode, (long) max);
          if (Collection.class.isAssignableFrom(v.getClass())
              && ((Collection<?>) v).size() > (int) max)
            throw new ValidationException(message, messageCode, (long) max);
          if (Map.class.isAssignableFrom(v.getClass()) && ((Map<?, ?>) v).size() > (int) max)
            throw new ValidationException(message, messageCode, (long) max);
          if (v.getClass().isArray() && ((Object[]) v).length > (int) max)
            throw new ValidationException(message, messageCode, (long) max);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().max(max).build());
      }
    };
  }

  // Min

  /**
   * Build a validator that checks that given value is greater than or equal to <code>min</code>
   * value, and uses {@link ValidationMessage#MAX} as validation error message.
   *
   * <p>Null values are ignored.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given min value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given min value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given min value
   *   <li>Collections, Maps and arrays: size/length is checked against given min value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param min Min value
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> min(double min) {
    return min(min, ValidationMessage.MIN);
  }

  /**
   * Build a validator that checks that given value is greater than or equal to <code>min</code>
   * value, and uses given {@link Localizable} <code>message</code> as validation error message.
   *
   * <p>Null values are ignored.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given min value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given min value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given min value
   *   <li>Collections, Maps and arrays: size/length is checked against given min value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param min Min value
   * @param message Validation error message
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  static <T> Validator<T> min(double min, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return min(min, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given value is greater than or equal to <code>min</code>
   * value.
   *
   * <p>Null values are ignored.
   *
   * <p>Supported data types: {@link CharSequence}, {@link Number}, {@link Collection}, {@link Map}
   * and arrays.
   *
   * <ul>
   *   <li>String: the string length is checked against given min value (converted to a long)
   *   <li>Integer numbers: the number value is checked against given min value (converted to a
   *       long)
   *   <li>Decimal numbers: the number value is checked against given min value
   *   <li>Collections, Maps and arrays: size/length is checked against given min value
   * </ul>
   *
   * <p>If the data type only supports integer max value validation (for example, a String length),
   * the given <code>max</code> value is treated as an integer value, casting it to an <code>int
   * </code> or a <code>long</code>.
   *
   * @param <T> Validator type
   * @param min Min value
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   * @throws UnsupportedValidationTypeException If value to validate is of an unsupported data type
   */
  @SuppressWarnings("serial")
  static <T> Validator<T> min(double min, String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {

          if (!v.getClass().isArray()
              && !TypeUtils.isNumber(v.getClass())
              && !CharSequence.class.isAssignableFrom(v.getClass())
              && !Collection.class.isAssignableFrom(v.getClass())
              && !Map.class.isAssignableFrom(v.getClass())) {
            // unsupported type
            throw new UnsupportedValidationTypeException(
                "Data type not supported by min validator: " + v.getClass().getName());
          }

          if (TypeUtils.isNumber(v.getClass())) {
            if (TypeUtils.isDecimalNumber(v.getClass())) {
              if (((Number) v).doubleValue() < min) {
                throw new ValidationException(message, messageCode, min);
              }
            } else {
              if (((Number) v).longValue() < (long) min) {
                throw new ValidationException(message, messageCode, (long) min);
              }
            }
          }
          if (CharSequence.class.isAssignableFrom(v.getClass())
              && ((CharSequence) v).length() < (int) min)
            throw new ValidationException(message, messageCode, (long) min);
          if (Collection.class.isAssignableFrom(v.getClass())
              && ((Collection<?>) v).size() < (int) min)
            throw new ValidationException(message, messageCode, (long) min);
          if (Map.class.isAssignableFrom(v.getClass()) && ((Map<?, ?>) v).size() < (int) min)
            throw new ValidationException(message, messageCode, (long) min);
          if (v.getClass().isArray() && ((Object[]) v).length < (int) min)
            throw new ValidationException(message, messageCode, (long) min);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().min(min).build());
      }
    };
  }

  // Pattern

  /**
   * Build a validator that checks that given value matches a regular expression, and uses {@link
   * ValidationMessage#PATTERN} as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @param regex Regular expression to match
   * @param flags Optional {@link PatternFlag} to considered when resolving the regular expression
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> pattern(String regex, PatternFlag... flags) {
    return pattern(regex, ValidationMessage.PATTERN, flags);
  }

  /**
   * Build a validator that checks that given value matches a regular expression, and uses given
   * {@link Localizable} <code>message</code> as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @param regex Regular expression to match
   * @param message Localizable error message
   * @param flags Optional {@link PatternFlag} to considered when resolving the regular expression
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> pattern(
      String regex, MessageSourceResolvable message, PatternFlag... flags) {
    Assert.notNull(message, "Validation error message must be not null");
    return pattern(regex, message.getDefaultMessage(), message.getCodes(), flags);
  }

  /**
   * Build a validator that checks that given value matches a regular expression.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Validator type
   * @param regex Regular expression to match
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @param flags Optional {@link PatternFlag} to considered when resolving the regular expression
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends CharSequence> Validator<T> pattern(
      String regex, String message, String[] messageCode, PatternFlag... flags) {
    Assert.hasText(regex, "Regular expression must be not null");
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          if (!Pattern.compile(regex, PatternFlag.asBitValue(flags)).matcher(v).matches()) {
            throw new ValidationException(message, messageCode);
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().pattern(regex).build());
      }
    };
  }

  // In

  /**
   * Build a validator that checks that given value is not <code>null</code> and equals to one of
   * the given values, using default {@link ValidationMessage#IN} ad validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param values Values to match
   * @return Validator
   */
  @SafeVarargs
  static <T> Validator<T> in(T... values) {
    return in(ValidationMessage.IN, values);
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> and equals to one of
   * the given values, using given {@link Localizable} message as validation error.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param message Validation error message
   * @param values Values to match
   * @return Validator
   */
  @SafeVarargs
  static <T> Validator<T> in(MessageSourceResolvable message, T... values) {
    Assert.notNull(message, "Validation error message must be not null");
    return in(message.getDefaultMessage(), message.getCodes(), values);
  }

  /**
   * Build a validator that checks that given value is not <code>null</code> and equals to one of
   * the given values.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param values Values to match
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  @SafeVarargs
  static <T> Validator<T> in(String message, String messageCode[], T... values) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("Value must be not null and not empty");
    }
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          for (T value : values) {
            if (v.equals(value)) {
              return;
            }
          }
        }
        throw new ValidationException(message, messageCode);
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().in(values).build());
      }
    };
  }

  // Not in

  /**
   * Build a validator that checks that given value not equals to any of the given values, using
   * default {@link ValidationMessage#NOT_IN} ad validation error message.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param values Values to exclude
   * @return Validator
   */
  @SafeVarargs
  static <T> Validator<T> notIn(T... values) {
    return notIn(ValidationMessage.NOT_IN, values);
  }

  /**
   * Build a validator that checks that given value not equals to any of the given values, using
   * given {@link Localizable} message as validation error.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param message Validation error message
   * @param values Values to exclude
   * @return Validator
   */
  @SafeVarargs
  static <T> Validator<T> notIn(MessageSourceResolvable message, T... values) {
    Assert.notNull(message, "Validation error message must be not null");
    return notIn(message.getDefaultMessage(), message.getCodes(), values);
  }

  /**
   * Build a validator that checks that given value not equals to any of the given values.
   *
   * <p>Supported data types: all
   *
   * @param <T> Value and validator type
   * @param values Values to exclude
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  @SafeVarargs
  static <T> Validator<T> notIn(String message, String[] messageCode, T... values) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("Value must be not null and not empty");
    }
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          for (T value : values) {
            if (v.equals(value)) {
              throw new ValidationException(message, messageCode);
            }
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().notIn(values).build());
      }
    };
  }

  // NotZero

  /**
   * Build a validator that checks that given {@link Number} value is not <code>0</code>, using
   * default {@link ValidationMessage#NOT_ZERO} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @return Validator
   */
  static <T extends Number> Validator<T> notZero() {
    return notZero(ValidationMessage.NOT_ZERO);
  }

  /**
   * Build a validator that checks that given {@link Number} value is not <code>0</code>, using
   * given {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Number> Validator<T> notZero(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return notZero(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given {@link Number} value is not <code>0</code>.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Number> Validator<T> notZero(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && Math.signum(v.intValue()) == 0) {
          throw new ValidationException(message, messageCode);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().notIn(0).build());
      }
    };
  }

  // NotNegative

  /**
   * Build a validator that checks that given {@link Number} value is not negative, using default
   * {@link ValidationMessage#NOT_NEGATIVE} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @return Validator
   */
  static <T extends Number> Validator<T> notNegative() {
    return notNegative(ValidationMessage.NOT_NEGATIVE);
  }

  /**
   * Build a validator that checks that given {@link Number} value is not negative, using given
   * {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Number> Validator<T> notNegative(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return notNegative(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given {@link Number} value is not negative.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Number> Validator<T> notNegative(String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && Math.signum(v.doubleValue()) < 0) {
          throw new ValidationException(message, messageCode);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().min(0).build());
      }
    };
  }

  // Digits

  /**
   * Build a validator that checks that given {@link Number} value is a number within accepted
   * range, using default {@link ValidationMessage#DIGITS} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param integral maximum number of integral digits accepted for this number (not negative)
   * @param fractional maximum number of fractional digits accepted for this number (not negative)
   * @return Validator
   */
  static <T extends Number> Validator<T> digits(int integral, int fractional) {
    return digits(integral, fractional, ValidationMessage.DIGITS);
  }

  /**
   * Build a validator that checks that given {@link Number} value is a number within accepted
   * range, using given {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param integral maximum number of integral digits accepted for this number (not negative)
   * @param fractional maximum number of fractional digits accepted for this number (not negative)
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Number> Validator<T> digits(
      int integral, int fractional, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return digits(integral, fractional, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given {@link Number} value is a number within accepted
   * range.
   *
   * <p>Supported data types: {@link Number}
   *
   * @param <T> Validator type
   * @param integral maximum number of integral digits accepted for this number (not negative)
   * @param fractional maximum number of fractional digits accepted for this number (not negative)
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Number> Validator<T> digits(
      int integral, int fractional, String message, String... messageCode) {
    if (integral < 0) {
      throw new IllegalArgumentException("Integral digits max number cannot be negative");
    }
    if (fractional < 0) {
      throw new IllegalArgumentException("Fractional digits max number cannot be negative");
    }
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          String string = null;
          if (TypeUtils.isDecimalNumber(v.getClass())) {
            BigDecimal bd =
                (v instanceof BigDecimal) ? (BigDecimal) v : BigDecimal.valueOf(v.doubleValue());
            string = bd.stripTrailingZeros().toPlainString();
          } else {
            BigInteger bi =
                (v instanceof BigInteger) ? (BigInteger) v : BigInteger.valueOf(v.longValue());
            string = bi.toString();
          }
          if (string != null) {
            if (string.startsWith("-")) {
              string = string.substring(1);
            }
            int index = string.indexOf('.');
            int itg = index < 0 ? string.length() : index;
            int fct = index < 0 ? 0 : string.length() - index - 1;
            if (itg > integral) {
              throw new ValidationException(message, messageCode);
            }
            if (fct > fractional) {
              throw new ValidationException(message, messageCode);
            }
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(
            ValidatorDescriptor.builder()
                .integerDigits(integral)
                .fractionDigits(fractional)
                .build());
      }
    };
  }

  // Past

  /**
   * Build a validator that checks that given {@link Date} value is in the past, using default
   * {@link ValidationMessage#PAST} message as validation error message.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @return Validator
   */
  static <T extends Date> Validator<T> past(boolean includeTime) {
    return past(includeTime, ValidationMessage.PAST);
  }

  /**
   * Build a validator that checks that given {@link Date} value is in the past, using given {@link
   * Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Date> Validator<T> past(boolean includeTime, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return past(includeTime, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given {@link Date} value is in the past.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Date> Validator<T> past(
      boolean includeTime, String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          if (!includeTime) {
            Date today = CalendarUtils.floorTime(Calendar.getInstance()).getTime();
            Date date = CalendarUtils.floorTime(v);
            if (today.equals(date) || date.after(today)) {
              throw new ValidationException(message, messageCode);
            }
          } else {
            if (v.getTime() >= System.currentTimeMillis()) {
              throw new ValidationException(message, messageCode);
            }
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().past().build());
      }
    };
  }

  // Future

  /**
   * Build a validator that checks that given {@link Date} value is in the future, using default
   * {@link ValidationMessage#FUTURE} message as validation error message.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @return Validator
   */
  static <T extends Date> Validator<T> future(boolean includeTime) {
    return future(includeTime, ValidationMessage.FUTURE);
  }

  /**
   * Build a validator that checks that given {@link Date} value is in the future, using given
   * {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Date> Validator<T> future(
      boolean includeTime, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return future(includeTime, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that given {@link Date} value is in the future.
   *
   * <p>Supported data types: {@link Date}
   *
   * @param <T> Validator type
   * @param includeTime Whether to include time in validation. If <code>false</code>, only
   *     year/month/day are considered.
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Date> Validator<T> future(
      boolean includeTime, String message, String... messageCode) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          if (!includeTime) {
            Date today = CalendarUtils.floorTime(Calendar.getInstance()).getTime();
            Date date = CalendarUtils.floorTime(v);
            if (today.equals(date) || date.before(today)) {
              throw new ValidationException(message, messageCode);
            }
          } else {
            if (v.getTime() <= System.currentTimeMillis()) {
              throw new ValidationException(message, messageCode);
            }
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().future().build());
      }
    };
  }

  // Less

  /**
   * Build a validator that checks that a value is less than given <code>compareTo</code> value, and
   * uses default {@link ValidationMessage#LESS_THAN} message as validation error message.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> lessThan(T compareTo) {
    return lessThan(compareTo, ValidationMessage.LESS_THAN);
  }

  /**
   * Build a validator that checks that a value is less than given <code>compareTo</code> value, and
   * uses given {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> lessThan(
      T compareTo, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return lessThan(compareTo, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that a value is less than given <code>compareTo</code> value.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Comparable<T>> Validator<T> lessThan(
      T compareTo, String message, String... messageCode) {
    Assert.notNull(compareTo, "Value to compare must be not null");
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && v.compareTo(compareTo) >= 0) {
          throw new ValidationException(message, messageCode, compareTo);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        if (TypeUtils.isNumber(compareTo.getClass())) {
          return Optional.of(
              ValidatorDescriptor.builder().max(((Number) compareTo)).exclusiveMax().build());
        }
        return Optional.empty();
      }
    };
  }

  /**
   * Build a validator that checks that a value is less than or equal to given <code>compareTo
   * </code> value, and uses default {@link ValidationMessage#LESS_OR_EQUAL} message as validation
   * error message. Supported data types: {@link Comparable}.
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> lessOrEqual(T compareTo) {
    return lessOrEqual(compareTo, ValidationMessage.LESS_OR_EQUAL);
  }

  /**
   * Build a validator that checks that a value is less than or equal to given <code>compareTo
   * </code> value, and uses given {@link Localizable} message as validation error message.
   * Supported data types: {@link Comparable}.
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> lessOrEqual(
      T compareTo, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return lessOrEqual(compareTo, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that a value is less than or equal to given <code>compareTo
   * </code> value.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Comparable<T>> Validator<T> lessOrEqual(
      T compareTo, String message, String... messageCode) {
    Assert.notNull(compareTo, "Value to compare must be not null");
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && v.compareTo(compareTo) > 0) {
          throw new ValidationException(message, messageCode, compareTo);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        if (TypeUtils.isNumber(compareTo.getClass())) {
          return Optional.of(ValidatorDescriptor.builder().max(((Number) compareTo)).build());
        }
        return Optional.empty();
      }
    };
  }

  // Greater

  /**
   * Build a validator that checks that a value is greater than given <code>compareTo</code> value,
   * and uses default {@link ValidationMessage#LESS_THAN} message as validation error message.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> greaterThan(T compareTo) {
    return greaterThan(compareTo, ValidationMessage.GREATER_THAN);
  }

  /**
   * Build a validator that checks that a value is greater than given <code>compareTo</code> value,
   * and uses given {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> greaterThan(
      T compareTo, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return greaterThan(compareTo, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that a value is greater than given <code>compareTo</code> value.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Comparable<T>> Validator<T> greaterThan(
      T compareTo, String message, String... messageCode) {
    Assert.notNull(compareTo, "Value to compare must be not null");
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && v.compareTo(compareTo) <= 0) {
          throw new ValidationException(message, messageCode, compareTo);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        if (TypeUtils.isNumber(compareTo.getClass())) {
          return Optional.of(
              ValidatorDescriptor.builder().min(((Number) compareTo)).exclusiveMin().build());
        }
        return Optional.empty();
      }
    };
  }

  /**
   * Build a validator that checks that a value is greater than or equal to given <code>compareTo
   * </code> value, and uses default {@link ValidationMessage#GREATER_OR_EQUAL} message as
   * validation error message. Supported data types: {@link Comparable}.
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> greaterOrEqual(T compareTo) {
    return greaterOrEqual(compareTo, ValidationMessage.GREATER_OR_EQUAL);
  }

  /**
   * Build a validator that checks that a value is greater than or equal to given <code>compareTo
   * </code> value, and uses given {@link Localizable} message as validation error message.
   * Supported data types: {@link Comparable}.
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @return Validator
   */
  static <T extends Comparable<T>> Validator<T> greaterOrEqual(
      T compareTo, MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return greaterOrEqual(compareTo, message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that a value is greater than or equal to given <code>compareTo
   * </code> value.
   *
   * <p>Supported data types: {@link Comparable}
   *
   * @param <T> Value and validator type
   * @param compareTo Value to compare
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends Comparable<T>> Validator<T> greaterOrEqual(
      T compareTo, String message, String... messageCode) {
    Assert.notNull(compareTo, "Value to compare must be not null");
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null && v.compareTo(compareTo) < 0) {
          throw new ValidationException(message, messageCode, compareTo);
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        if (TypeUtils.isNumber(compareTo.getClass())) {
          return Optional.of(ValidatorDescriptor.builder().min(((Number) compareTo)).build());
        }
        return Optional.empty();
      }
    };
  }

  // Email

  /**
   * Build a validator that checks that the value is a valid e-mail address using RFC822 format
   * rules, and uses default {@link ValidationMessage#EMAIL} message as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Value and validator type
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> email() {
    return email(ValidationMessage.EMAIL);
  }

  /**
   * Build a validator that checks that the value is a valid e-mail address using RFC822 format
   * rules, and uses given {@link Localizable} message as validation error message.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Value and validator type
   * @param message Validation error message
   * @return Validator
   */
  static <T extends CharSequence> Validator<T> email(MessageSourceResolvable message) {
    Assert.notNull(message, "Validation error message must be not null");
    return email(message.getDefaultMessage(), message.getCodes());
  }

  /**
   * Build a validator that checks that the value is a valid e-mail address using RFC822 format
   * rules.
   *
   * <p>Supported data types: {@link CharSequence}
   *
   * @param <T> Value and validator type
   * @param message Validation error message
   * @param messageCode Optional validation error message localization code
   * @return Validator
   */
  @SuppressWarnings("serial")
  static <T extends CharSequence> Validator<T> email(String message, String... codes) {
    return new BuiltinValidator<T>() {

      @Override
      public void validate(T v) throws ValidationException {
        if (v != null) {
          if (!Pattern.compile(FormatUtils.EMAIL_RFC822_REGEXP_PATTERN).matcher(v).matches()) {
            throw new ValidationException(message, codes);
          }
        }
      }

      @Override
      public Optional<ValidatorDescriptor> getDescriptor() {
        return Optional.of(ValidatorDescriptor.builder().email().build());
      }
    };
  }

  // Support

  /** Validation message localization code common prefix */
  static final String DEFAULT_MESSAGE_CODE_PREFIX = "holon.common.validation.message.";

  public static final String DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER = "&";

  /** Validation messages for common validators */
  enum ValidationMessage implements MessageSourceResolvable {

    /** Default <em>null</em> validation error message */
    NULL("Value must be null", DEFAULT_MESSAGE_CODE_PREFIX + "null"),

    /** Default <em>notNull</em> validation error message */
    NOT_NULL("Value is required", DEFAULT_MESSAGE_CODE_PREFIX + "notnull"),

    /** Default <em>notEmpty</em> validation error message */
    NOT_EMPTY("Value is required and must not be empty", DEFAULT_MESSAGE_CODE_PREFIX + "notempty"),

    /** Default <em>notBlank</em> validation error message */
    NOT_BLANK("Value is required and must not be blank", DEFAULT_MESSAGE_CODE_PREFIX + "notblank"),

    /** Default <em>max</em> validation error message */
    MAX(
        "Value too large. Maximum is " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "max"),

    /** Default <em>min</em> validation error message */
    MIN(
        "Value too small. Minimum is " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "min"),

    /** Default <em>pattern</em> validation error message */
    PATTERN("Invalid value", DEFAULT_MESSAGE_CODE_PREFIX + "pattern"),

    /** Default <em>in</em> validation error message */
    IN("Invalid value", DEFAULT_MESSAGE_CODE_PREFIX + "in"),

    /** Default <em>notIn</em> validation error message */
    NOT_IN("Invalid value", DEFAULT_MESSAGE_CODE_PREFIX + "notin"),

    /** Default <em>is</em> validation error message */
    DIGITS("Invalid number", DEFAULT_MESSAGE_CODE_PREFIX + "digits"),

    /** Default <em>notNegative</em> validation error message */
    NOT_NEGATIVE("Negative values are not allowed", DEFAULT_MESSAGE_CODE_PREFIX + "notnegative"),

    /** Default <em>notZero</em> validation error message */
    NOT_ZERO("0 is not allowed", DEFAULT_MESSAGE_CODE_PREFIX + "notzero"),

    /** Default <em>past</em> validation error message */
    PAST("Date must be in the past", DEFAULT_MESSAGE_CODE_PREFIX + "past"),

    /** Default <em>future</em> validation error message */
    FUTURE("Date must be in the future", DEFAULT_MESSAGE_CODE_PREFIX + "future"),

    /** Default <em>lessThan</em> validation error message */
    LESS_THAN(
        "Value must be less than " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "lt"),

    /** Default <em>lessOrEqual</em> validation error message */
    LESS_OR_EQUAL(
        "Value must be less than or equal to " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "loe"),

    /** Default <em>greaterThan</em> validation error message */
    GREATER_THAN(
        "Value must be greater than " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "gt"),

    /** Default <em>greaterOrEqual</em> validation error message */
    GREATER_OR_EQUAL(
        "Value must be greater than or equal to " + DEFAULT_MESSAGE_ARGUMENT_PLACEHOLDER,
        DEFAULT_MESSAGE_CODE_PREFIX + "goe"),

    /** Default <em>email</em> validation error message */
    EMAIL("Invalid e-mail address", DEFAULT_MESSAGE_CODE_PREFIX + "email");

    private final String message;
    private final String[] messageCode;

    /**
     * Constructor
     *
     * @param message Error message
     * @param messageCode Error message localization code
     */
    private ValidationMessage(String message, String... codes) {
      this.message = message;
      this.messageCode = codes;
    }

    /*
     * (non-Javadoc)
     * @see com.holonplatform.core.i18n.Localizable#getMessageCode()
     */
    @Override
    public String[] getCodes() {
      return messageCode;
    }

    /*
     * (non-Javadoc)
     * @see com.holonplatform.core.i18n.Localizable#getMessage()
     */
    @Override
    public String getDefaultMessage() {
      return message;
    }
  }

  /** Pattern validation regexp flags */
  public enum PatternFlag {

    /**
     * Enables Unix lines mode.
     *
     * @see Pattern#UNIX_LINES
     */
    UNIX_LINES(Pattern.UNIX_LINES),

    /**
     * Enables case-insensitive matching.
     *
     * @see Pattern#CASE_INSENSITIVE
     */
    CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE),

    /**
     * Permits whitespace and comments in pattern.
     *
     * @see Pattern#COMMENTS
     */
    COMMENTS(Pattern.COMMENTS),

    /**
     * Enables multiline mode.
     *
     * @see Pattern#MULTILINE
     */
    MULTILINE(Pattern.MULTILINE),

    /**
     * Enables dotall mode.
     *
     * @see Pattern#DOTALL
     */
    DOTALL(Pattern.DOTALL),

    /**
     * Enables Unicode-aware case folding.
     *
     * @see Pattern#UNICODE_CASE
     */
    UNICODE_CASE(Pattern.UNICODE_CASE),

    /**
     * Enables canonical equivalence.
     *
     * @see Pattern#CANON_EQ
     */
    CANON_EQ(Pattern.CANON_EQ);

    private final int value;

    private PatternFlag(int value) {
      this.value = value;
    }

    /**
     * Get the regex flag value.
     *
     * @return flag value as defined in {@link Pattern}
     */
    public int getValue() {
      return value;
    }

    /**
     * Get given flags as bit mask.
     *
     * @param flags Flags
     * @return Bit mask value
     */
    public static int asBitValue(PatternFlag[] flags) {
      int bm = 0;
      if (flags != null && flags.length > 0) {
        for (PatternFlag flg : flags) {
          bm = bm | flg.getValue();
        }
      }
      return bm;
    }

    /**
     * Get the {@link PatternFlag} which corresponds to given regex flag value
     *
     * @param flag Regex flag
     * @return PatternFlag, or <code>null</code> if none matches
     */
    public static PatternFlag fromFlag(int flag) {
      for (PatternFlag pf : values()) {
        if (pf.getValue() == flag) {
          return pf;
        }
      }
      return null;
    }
  }

  /**
   * Exception thrown by a {@link Validators} when given value to validate is of an unsupported
   * type.
   */
  public class UnsupportedValidationTypeException extends RuntimeException {

    private static final long serialVersionUID = 2476169346408053520L;

    /**
     * Constructor
     *
     * @param message Error message
     */
    public UnsupportedValidationTypeException(String message) {
      super(message);
    }
  }
}
