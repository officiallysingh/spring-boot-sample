package com.ksoot.metadata.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ClassWriteConverter implements Converter<Class<?>, String> {

  @Override
  public String convert(Class<?> clazz) {
    return clazz.getName();
  }
}
