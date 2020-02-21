package com.devadmin.vicky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for Vicky application
 */
@SpringBootApplication(scanBasePackages = "com.devadmin")
@EnableScheduling
public class VickyApplication {

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VickyApplication.class, args);
    }
}
