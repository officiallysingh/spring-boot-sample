package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ksoot.metadata.core.property.Property;
import com.ksoot.metadata.core.property.PropertySupport;
import java.io.IOException;

public class PropertyBeanSerializer<T extends Property<?>>
    extends JsonSerializer<PropertySupport<T>> {

  @Override
  public void serialize(PropertySupport<T> value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    // TODO Auto-generated method stub

  }
}
