package com.ksoot.metadata.config;

import com.ksoot.metadata.core.util.ClassUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ClassReadConverter implements Converter<String, Class<?>> {

  @Override
  public Class<?> convert(String className) {
    try {
      return ClassUtils.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Class with name: " + className + " not found", e);
    }
  }
}
