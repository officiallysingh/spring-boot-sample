package com.ksoot.metadata.core.jackson;

public enum PropertySerializationMode {
  VALUE,
  /**
   * Serialize only the {@link Path} type properties.
   *
   * <p>The path name, obtained through {@link Path#getName()}, is used as serialized property name.
   *
   * <p>This is the default serialization mode.
   */
  NAME_VALUE,

  NAME_VALUE_TYPE,

  META_DATA,

  /**
   * Serialize all the properties.
   *
   * <p>The property name, obtained through {@link Property#getName()}, is used as serialized
   * property name.
   */
  ALL;

  /**
   * Get the default {@link PropertySerializationMode}.
   *
   * @return The default {@link PropertySerializationMode}
   */
  public static PropertySerializationMode getDefault() {
    return NAME_VALUE;
  }
}
