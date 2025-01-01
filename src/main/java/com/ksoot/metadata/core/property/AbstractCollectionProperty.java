package com.ksoot.metadata.core.property;

import com.ksoot.metadata.core.validator.Validator;
import java.util.Collection;
import java.util.List;

public abstract class AbstractCollectionProperty<E, C extends Collection<E>>
    extends AbstractProperty<C> implements CollectionPropertyTrait<E, C> {

  private static final long serialVersionUID = 1L;

  protected Class<? extends E> elementType;

  protected AbstractCollectionProperty(
      final String name,
      final C value,
      final Class<? extends C> type,
      Class<? extends E> elementType) {
    super(name, value, type);
    this.elementType = elementType;
  }

  protected AbstractCollectionProperty(
      final String name,
      final C value,
      final Class<? extends C> type,
      Class<? extends E> elementType,
      final List<Validator<C>> validators) {
    this(name, value, type, elementType);
    this.validators = validators;
  }

  @Override
  public Class<? extends E> getElementType() {
    return this.elementType;
  }
}
