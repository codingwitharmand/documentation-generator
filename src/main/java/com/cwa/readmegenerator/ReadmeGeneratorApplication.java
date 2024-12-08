package com.cwa.readmegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReadmeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadmeGeneratorApplication.class, args);
    }

}
