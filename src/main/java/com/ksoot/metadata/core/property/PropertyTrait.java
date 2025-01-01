package com.ksoot.metadata.core.property;

import java.io.Serializable;

public interface PropertyTrait<T> extends Serializable {

  /**
   * Get the name which identifies this property.
   *
   * @return The property name (should be not null)
   */
  String getName();

  T getValue();

  /**
   * Get the type of values supported by this property.
   *
   * @return Property value type (not null)
   */
  Class<?> getType();
}
