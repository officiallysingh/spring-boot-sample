package com.ksoot.metadata.core.jackson;

public @interface PropertyMetadata {

  String id() default "id";

  String name() default "metadata";
}
