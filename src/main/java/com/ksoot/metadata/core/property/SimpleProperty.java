package com.ksoot.metadata.core.property;

import com.ksoot.metadata.core.validator.Validator;
import java.util.List;

public class SimpleProperty<T> extends AbstractProperty<T> {

  private static final long serialVersionUID = 1L;

  SimpleProperty(final String name, final T value, final Class<? extends T> type) {
    super(name, value, type);
  }

  SimpleProperty(
      final String name,
      final T value,
      final Class<? extends T> type,
      final List<Validator<T>> validators) {
    super(name, value, type, validators);
  }

  public static <T> SimpleProperty<T> create(
      String name, final T value, final Class<? extends T> type) {
    return new SimpleProperty<>(name, value, type);
  }

  public static <T> SimpleProperty<T> create(
      String name,
      final T value,
      final Class<? extends T> type,
      final List<Validator<T>> validators) {
    return new SimpleProperty<>(name, value, type, validators);
  }
}
