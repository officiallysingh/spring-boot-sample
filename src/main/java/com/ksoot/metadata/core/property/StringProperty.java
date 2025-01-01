package com.ksoot.metadata.core.property;

public class StringProperty extends SimpleProperty<String> {

  private static final long serialVersionUID = 1L;

  private StringProperty(final String name, final String value) {
    super(name, value, String.class);
  }

  public static StringProperty create(String name, final String value) {
    return new StringProperty(name, value);
  }
}
