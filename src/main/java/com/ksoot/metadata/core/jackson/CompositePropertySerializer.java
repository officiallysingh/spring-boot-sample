package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;
import com.ksoot.metadata.core.property.TreeNode;
import java.io.IOException;
import org.apache.commons.collections4.CollectionUtils;

public class CompositePropertySerializer<T extends Property<?>>
    extends StdSerializer<CompositeProperty<T>> implements ContextualSerializer {

  private static final long serialVersionUID = 1L;

  private PropertySerializationMode serializationMode = PropertySerializationMode.NAME_VALUE;

  private boolean unwrap = false;

  public CompositePropertySerializer() {
    super(CompositeProperty.class, false);
  }

  public CompositePropertySerializer(PropertySerializationMode serializationMode, boolean unwrap) {
    super(CompositeProperty.class, false);
    this.serializationMode = serializationMode;
    this.unwrap = unwrap;
  }

  @Override
  public void serialize(
      CompositeProperty<T> property, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {

    PropertySerializationMode _serializationMode =
        (PropertySerializationMode) serializers.getAttribute("SERIALIZATION_MODE");
    if (!this.unwrap) {
      gen.writeStartObject();
    }
    gen.writePOJOField(property.getNode().getName(), property.getNode().getValue());
    if (CollectionUtils.isNotEmpty(property.getChildren())) {
      gen.writeObjectFieldStart(property.getChildNodeName());
      for (TreeNode<T> child : property.getChildren()) {
        gen.writeObjectField(child.getNode().getName(), child.getNode().getValue());
      }
      gen.writeEndObject();
    }
    if (!this.unwrap) {
      gen.writeEndObject();
    }
  }

  @Override
  public boolean isUnwrappingSerializer() {
    return this.unwrap;
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {
    PropertySerializationMode _serializationMode = PropertySerializationMode.NAME_VALUE;
    boolean _unwrap = false;

    PropertySerialization propertySerialization = null;
    JsonUnwrapped jsonUnwrapped = null;
    if (property != null) {
      propertySerialization = property.getAnnotation(PropertySerialization.class);
      if (propertySerialization == null) {
        propertySerialization = property.getContextAnnotation(PropertySerialization.class);
      }
      jsonUnwrapped = property.getAnnotation(JsonUnwrapped.class);
      if (jsonUnwrapped == null) {
        jsonUnwrapped = property.getContextAnnotation(JsonUnwrapped.class);
      }
    }
    if (propertySerialization != null) {
      _serializationMode = propertySerialization.mode();
    }
    if (jsonUnwrapped != null) {
      _unwrap = jsonUnwrapped.enabled();
    }
    return new CompositePropertySerializer<>(_serializationMode, _unwrap);
  }
}
