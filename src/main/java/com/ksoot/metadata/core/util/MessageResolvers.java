package com.ksoot.metadata.core.util;

import org.apache.commons.lang3.builder.Builder;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;

public class MessageResolvers {

  public static MessageBuilder builder() {
    return new MessageBuilder();
  }

  public static class MessageBuilder implements Builder<MessageSourceResolvable> {

    private String defaultMessage;

    private String[] codes;

    private Object[] arguments;

    public MessageBuilder codes(String... codes) {
      this.codes = codes;
      return this;
    }

    public MessageBuilder defaultMessage(String defaultMessage) {
      this.defaultMessage = defaultMessage;
      return this;
    }

    public MessageBuilder arguments(Object... arguments) {
      this.arguments = arguments;
      return this;
    }

    public MessageSourceResolvable build() {
      return new DefaultMessageSourceResolvable(codes, arguments, defaultMessage);
    }
  }

  private MessageResolvers() {
    throw new IllegalStateException(
        "MessageResolvers factory class, not supposed to be instantiated");
  }
}
