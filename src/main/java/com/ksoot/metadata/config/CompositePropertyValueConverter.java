package com.ksoot.metadata.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
// import com.ksoot.common.spring.boot.config.DefaultBeanRegistry;
import com.ksoot.common.config.DefaultBeanRegistry;
import com.ksoot.metadata.core.jackson.PropertyJsonException;
import com.ksoot.metadata.core.jackson.PropertySerializationMode;
import com.ksoot.metadata.core.property.CompositeProperty;
import com.ksoot.metadata.core.property.Property;
import org.bson.Document;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor
public class CompositePropertyValueConverter
    implements PropertyValueConverter<
        CompositeProperty<Property<?>>, Document, MongoConversionContext> {

  //	private final ObjectMapper objectMapper;

  @Override
  public CompositeProperty<Property<?>> read(Document document, MongoConversionContext context) {
    try {
      ObjectMapper objectMapper = DefaultBeanRegistry.getBean(ObjectMapper.class);
      return objectMapper.readValue(document.toJson(), CompositeProperty.class);
    } catch (JsonProcessingException e) {
      throw new PropertyJsonException(
          "MongoDB Write coverter exception while converting CompositeProperty to String");
    }
  }

  @Override
  public Document write(CompositeProperty<Property<?>> property, MongoConversionContext context) {
    try {
      ObjectMapper objectMapper = DefaultBeanRegistry.getBean(ObjectMapper.class);
      String doc =
          objectMapper
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
