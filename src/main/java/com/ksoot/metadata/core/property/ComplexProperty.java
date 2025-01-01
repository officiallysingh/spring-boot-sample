package com.ksoot.metadata.core.property;

import com.google.common.collect.Maps;
import java.util.Map;

public class ComplexProperty<T> extends AbstractProperty<T> implements AttributeSupport {

  private static final long serialVersionUID = 1L;

  ComplexProperty(String name, T value, Class<? extends T> type) {
    super(name, value, type);
  }

  ComplexProperty(String name, T value, Class<? extends T> type, Map<String, String> attributes) {
    super(name, value, type);
    this.attributes = attributes;
  }

  private Map<String, String> attributes = Maps.newLinkedHashMap();

  @Override
  public AttributeSupport addAttribute(String key, String value) {
    this.attributes.put(key, value);
    return this;
  }

  @Override
  public AttributeSupport addAttributes(Map<String, String> attributes) {
    this.attributes.putAll(attributes);
    return this;
  }

  @Override
  public Map<String, String> getAttributes() {
    return this.attributes;
  }

  @Override
  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  @Override
  public void clearAttributes() {
    this.attributes.clear();
  }
}
