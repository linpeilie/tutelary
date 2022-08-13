package com.tutelary.config;

import com.tutelary.common.processor.WebServerMessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.TutelaryWebServer;
import com.tutelary.server.properties.ServerEndpointConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(value = ServerEndpointConfig.class)
public class WebServerConfig {

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public TutelaryWebServer tutelaryWebServer(ServerEndpointConfig serverEndpointConfig, List<WebServerMessageProcessor<?>> messageProcessors) {
        MessageProcessorManager messageProcessorManager = new MessageProcessorManager();
        messageProcessors.forEach(messageProcessorManager::register);
        return new TutelaryWebServer(serverEndpointConfig, messageProcessorManager);
    }

}
