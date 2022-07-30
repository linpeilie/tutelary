package com.tutelary.config;

import com.tutelary.processor.MessageProcessor;
import com.tutelary.server.TutelaryServer;
import com.tutelary.server.properties.ServerEndpointConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties (value = ServerEndpointConfig.class)
public class ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public TutelaryServer tutelaryServer(ServerEndpointConfig serverEndpointConfig, List<MessageProcessor<?>> messageProcessors) {
        return new TutelaryServer(serverEndpointConfig, messageProcessors);
    }

}
