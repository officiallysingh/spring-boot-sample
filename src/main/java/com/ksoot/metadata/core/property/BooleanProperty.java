package com.ksoot.metadata.core.property;

public class BooleanProperty extends SimpleProperty<Boolean> {

  private static final long serialVersionUID = 1L;

  private BooleanProperty(final String name, final Boolean value) {
    super(name, value, Boolean.class);
  }

  public static BooleanProperty create(String name, final Boolean value) {
    return new BooleanProperty(name, value);
  }
}
