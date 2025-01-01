package com.ksoot.metadata.core.property;

import com.ksoot.metadata.core.validator.Validator;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapProperty<K, V, T extends Map<K, V>> extends AbstractProperty<T>
    implements MapPropertyTrait<K, V, T> {

  private static final long serialVersionUID = 1L;

  protected Class<? extends K> keyType;

  protected Class<? extends V> valueType;

  protected AbstractMapProperty(
      final String name,
      final T value,
      final Class<? extends T> type,
      final Class<? extends K> keyType,
      final Class<? extends V> valueType) {
    super(name, value, type);
    this.keyType = keyType;
    this.valueType = valueType;
  }

  protected AbstractMapProperty(
      final String name,
      final T value,
      final Class<? extends T> type,
      final Class<? extends K> keyType,
      final Class<? extends V> valueType,
      final List<Validator<T>> validators) {
    super(name, value, type, validators);
    this.keyType = keyType;
    this.valueType = valueType;
  }

  @Override
  public Class<? extends K> getKeyType() {
    return this.keyType;
  }

  @Override
  public Class<? extends V> getValueType() {
    return this.valueType;
  }
}
