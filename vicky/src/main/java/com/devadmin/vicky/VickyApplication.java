package com.devadmin.vicky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.devadmin")
public class VickyApplication {
  public static void main(String[] args) {
    SpringApplication.run(VickyApplication.class, args);
  }
}
