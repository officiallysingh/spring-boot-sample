package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;

/**
 * Jackson module with {@link PropertyBox} serialization and deserialization capabilities.
 *
 * @since 5.1.0
 */
public class PropertyModule extends Module {

  private final SimpleSerializers serializers = new SimpleSerializers();
  private final SimpleDeserializers deserializers = new SimpleDeserializers();

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PropertyModule() {
    serializers.addSerializer(Property.class, new PropertySerializer());
    serializers.addSerializer(CompositeProperty.class, new CompositePropertySerializer());

    deserializers.addDeserializer(CompositeProperty.class, new CompositePropertyDeserializer());
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fasterxml.jackson.databind.Module#getModuleName()
   */
  @Override
  public String getModuleName() {
    return PropertyModule.class.getName();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fasterxml.jackson.databind.Module#version()
   */
  @Override
  public Version version() {
    return new Version(5, 0, 0, null, null, null);
  }

  /*
   * (non-Javadoc)
   *
   * @see com.fasterxml.jackson.databind.Module#setupModule(com.fasterxml.jackson.
   * databind.Module.SetupContext)
   */
  @Override
  public void setupModule(SetupContext context) {
    context.addSerializers(serializers);
    context.addDeserializers(deserializers);
  }
}
