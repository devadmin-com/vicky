package com.devadmin.vicky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.devadmin"})
public class VickyApplication {

  public static void main(String[] args) {
    SpringApplication.run(VickyApplication.class, args);
  }
}
