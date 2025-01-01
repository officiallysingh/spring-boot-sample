package com.ksoot.metadata.core.property;

import java.util.Optional;

public interface PropertySupport<T extends Property<?>> {

  PropertySupport<T> addProperty(String path, T property);

  //	PropertySupport<T> addProperties(Map<String, T> properties);

  Optional<T> findProperty(String path);
}
