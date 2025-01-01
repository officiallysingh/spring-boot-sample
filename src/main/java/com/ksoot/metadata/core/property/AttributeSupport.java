package com.ksoot.metadata.core.property;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Map;

public interface AttributeSupport {

  AttributeSupport addAttribute(String key, String value);

  @JsonAnyGetter
  AttributeSupport addAttributes(Map<String, String> attributes);

  @JsonAnyGetter
  Map<String, String> getAttributes();

  @JsonAnySetter
  void setAttributes(Map<String, String> attributes);

  void clearAttributes();
}
