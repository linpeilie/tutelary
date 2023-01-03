package com.tutelary.config;

import com.tutelary.server.properties.ServerEndpointConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ServerEndpointConfig.class)
public class ServerConfig {

//    @Bean(initMethod = "start", destroyMethod = "destroy")
//    public TutelaryServer tutelaryServer(ServerEndpointConfig serverEndpointConfig,
//                                         List<RequestHandler> requestHandlers) {
//
//        return new TutelaryServer(serverEndpointConfig, requestHandlers);
//    }

}
