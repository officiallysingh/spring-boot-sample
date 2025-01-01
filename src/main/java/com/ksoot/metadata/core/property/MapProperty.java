package com.ksoot.metadata.core.property;

import java.util.Map;

public class MapProperty<K, V, T extends Map<K, V>> extends AbstractMapProperty<K, V, T> {

  private static final long serialVersionUID = 1L;

  private MapProperty(
      final String name,
      final T value,
      final Class<? extends T> type,
      final Class<? extends K> keyType,
      final Class<? extends V> valueType) {
    super(name, value, type, keyType, valueType);
  }

  public static <K, V, T extends Map<K, V>> MapProperty<K, V, T> create(
      String name,
      final T value,
      final Class<? extends T> type,
      final Class<? extends K> keyType,
      final Class<? extends V> valueType) {
    return new MapProperty<>(name, value, type, keyType, valueType);
  }
}
