package com.ksoot.metadata.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.ksoot.metadata.core.jackson.PropertyJsonException;
import com.ksoot.metadata.core.jackson.PropertySerializationMode;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
@RequiredArgsConstructor
public class CompositePropertyWriteConverter
    implements Converter<CompositeProperty<Property<?>>, Document> {

  private final ObjectMapper objectMapper;

  @Override
  public Document convert(CompositeProperty<Property<?>> property) {
    try {
      String doc =
          this.objectMapper
              .writer(
                  ContextAttributes.getEmpty()
                      .withPerCallAttribute(
                          "SERIALIZATION_MODE", PropertySerializationMode.META_DATA))
              .writeValueAsString(property);
      return Document.parse(doc);
    } catch (JsonProcessingException e) {
      throw new PropertyJsonException(
          "MongoDB Write coverter exception while converting CompositeProperty to String");
    }
  }
}
