package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ksoot.metadata.core.property.Property;
import java.io.IOException;

public class PropertySerializer<T> extends JsonSerializer<Property<T>> {

  @Override
  public void serialize(Property<T> property, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writePOJOField(property.getName(), property.getValue());
    gen.writeEndObject();
  }
}
