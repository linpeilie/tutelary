package com.tutelary;

import com.tutelary.server.properties.ServerEndpointConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class TutelaryServerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(TutelaryServerBootstrap.class, args);
    }

}
