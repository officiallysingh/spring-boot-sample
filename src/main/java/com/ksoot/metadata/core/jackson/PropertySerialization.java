package com.ksoot.metadata.core.jackson;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface PropertySerialization {

  PropertySerializationMode mode() default PropertySerializationMode.NAME_VALUE;

  PropertyMetadata metadata() default @PropertyMetadata();
}
