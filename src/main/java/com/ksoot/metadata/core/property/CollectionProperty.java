package com.ksoot.metadata.core.property;

import com.ksoot.metadata.core.validator.Validator;
import java.util.Collection;
import java.util.List;

public class CollectionProperty<E, C extends Collection<E>>
    extends AbstractCollectionProperty<E, C> {

  private static final long serialVersionUID = 1L;

  CollectionProperty(
      final String name,
      final C value,
      Class<? extends C> type,
      final Class<? extends E> elementType) {
    super(name, value, type, elementType);
  }

  CollectionProperty(
      final String name,
      final C value,
      Class<? extends C> type,
      final Class<? extends E> elementType,
      final List<Validator<C>> validators) {
    super(name, value, type, elementType, validators);
  }

  public static <E, C extends Collection<E>> CollectionProperty<E, C> create(
      final String name,
      final C value,
      Class<? extends C> type,
      final Class<? extends E> elementType) {
    return new CollectionProperty<>(name, value, type, elementType);
  }

  public static <E, C extends Collection<E>> CollectionProperty<E, C> create(
      final String name,
      final C value,
      Class<? extends C> type,
      final Class<? extends E> elementType,
      final List<Validator<C>> validators) {
    return new CollectionProperty<>(name, value, type, elementType, validators);
  }
}
