package com.omniterasoft.springboot.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @OpenAPIDefinition(servers = {@Server(url = "${server.servlet.context-path}")})
public class SpringBootSampleApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpringBootSampleApplication.class, args);
  }
}
