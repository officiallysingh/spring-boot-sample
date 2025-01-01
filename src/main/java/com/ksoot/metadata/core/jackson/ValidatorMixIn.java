package com.ksoot.metadata.core.jackson;

// import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
// import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ksoot.problem.core.Problem;
// import com.fasterxml.jackson.annotation.JsonPropertyOrder;
// import com.fasterxml.jackson.annotation.JsonTypeInfo;
// import com.ksoot.common.exceptionhandling.DefaultProblem;
// import com.ksoot.common.exceptionhandling.Problem;
import java.util.Map;

// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.EXISTING_PROPERTY,
//        property = "type",
//        defaultImpl = DefaultProblem.class,
//        visible = true)
// @JsonInclude(NON_EMPTY)
// @JsonPropertyOrder({ "code", "title", "message", "details" })
interface ValidatorMixIn extends Problem {

  @JsonProperty("code")
  @Override
  String getCode();

  @JsonProperty("title")
  @Override
  String getTitle();

  //    @JsonProperty("message")
  //    @Override
  //    String getMessage();

  @JsonProperty("details")
  @Override
  String getDetail();

  @JsonAnyGetter
  @Override
  Map<String, Object> getParameters();
}
