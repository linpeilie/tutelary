package com.tutelary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class TutelaryServerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(TutelaryServerBootstrap.class, args);
    }

}
