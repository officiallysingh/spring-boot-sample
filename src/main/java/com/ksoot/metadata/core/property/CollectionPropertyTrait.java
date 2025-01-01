package com.ksoot.metadata.core.property;

import java.util.Collection;

public interface CollectionPropertyTrait<E, C extends Collection<E>> extends Property<C> {

  /**
   * Get the collection elements type.
   *
   * @return collection elements type
   */
  Class<? extends E> getElementType();
}
