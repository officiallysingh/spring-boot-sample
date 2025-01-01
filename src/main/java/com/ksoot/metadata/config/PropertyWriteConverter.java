package com.ksoot.metadata.config;

import com.ksoot.metadata.core.property.Property;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class PropertyWriteConverter implements Converter<Property<?>, Pair<String, Object>> {

  @Override
  public Pair<String, Object> convert(Property<?> property) {
    return Pair.of(property.getName(), property.getValue());
  }
}
