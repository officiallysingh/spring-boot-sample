package com.ksoot.metadata.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksoot.metadata.core.jackson.PropertyJsonException;
import com.ksoot.metadata.core.property.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
@RequiredArgsConstructor
public class PropertyReadConverter implements Converter<String, Property<?>> {

  private final ObjectMapper objectMapper;

  @Override
  public Property<?> convert(String propertyString) {
    try {
      return this.objectMapper.readValue(propertyString, Property.class);
    } catch (JsonProcessingException e) {
      throw new PropertyJsonException(
          "MongoDB Read coverter exception while converting String to Property");
    }
  }
}
