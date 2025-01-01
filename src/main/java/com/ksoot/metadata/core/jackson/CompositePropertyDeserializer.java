package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;
import java.io.IOException;

public class CompositePropertyDeserializer<T extends Property<?>>
    extends StdDeserializer<CompositeProperty<T>> implements ContextualDeserializer {

  private static final long serialVersionUID = 1L;

  private PropertySerializationMode serializationMode = PropertySerializationMode.NAME_VALUE;

  public CompositePropertyDeserializer() {
    super(CompositeProperty.class);
  }

  public CompositePropertyDeserializer(
      PropertySerializationMode serializationMode, boolean unwrap) {
    super(CompositeProperty.class);
    this.serializationMode = serializationMode;
    //		this.unwrap = unwrap;
  }

  //	@Override
  //	public void serialize(CompositeProperty<T> property, JsonGenerator gen, SerializerProvider
  // serializers)
  //			throws IOException {
  //		PropertySerializationMode _serializationMode = (PropertySerializationMode) serializers
  //				.getAttribute("SERIALIZATION_MODE");
  //		if (!this.unwrap) {
  //			gen.writeStartObject();
  //		}
  //		gen.writePOJOField(property.getNode().getName(), property.getNode().getValue());
  //		if (CollectionUtils.isNotEmpty(property.getChildren())) {
  //			gen.writeObjectFieldStart(property.getChildNodeName());
  //			for (TreeNode<T> child : property.getChildren()) {
  //				gen.writeObjectField(child.getNode().getName(), child.getNode().getValue());
  //			}
  //			gen.writeEndObject();
  //		}
  //		if (!this.unwrap) {
  //			gen.writeEndObject();
  //		}
  //	}

  //	@Override
  //	public boolean isUnwrappingSerializer() {
  //		return this.unwrap;
  //	}

  @Override
  public CompositeProperty<T> deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JacksonException {

    Object parent = p.getParsingContext().getParent().getCurrentValue();
    System.out.println(p);
    System.out.println(ctxt);
    return null;
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
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
    return new CompositePropertyDeserializer<>(_serializationMode, _unwrap);
  }
}
