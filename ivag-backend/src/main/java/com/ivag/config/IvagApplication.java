package com.ivag.config;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ivag")
@ComponentScan("com.ivag")
public class IvagApplication {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(IvagApplication.class, args);
    }

}


