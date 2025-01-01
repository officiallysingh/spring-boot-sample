package com.ksoot.metadata.core.property;

import java.util.Map;

public interface MapPropertyTrait<K, V, T extends Map<K, V>> extends Property<T> {

  Class<? extends K> getKeyType();

  Class<? extends V> getValueType();
}
