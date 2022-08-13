package com.tutelary.config;

import com.tutelary.common.listener.ServerEventListener;
import com.tutelary.common.processor.ServerMessageProcessor;
import com.tutelary.common.processor.WebServerMessageProcessor;
import com.tutelary.event.ChannelEvents;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.server.TutelaryServer;
import com.tutelary.server.TutelaryWebServer;
import com.tutelary.server.properties.ServerEndpointConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(value = ServerEndpointConfig.class)
public class ServerConfig {

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public TutelaryServer tutelaryServer(ServerEndpointConfig serverEndpointConfig,
                                         List<ServerMessageProcessor<?>> serverMessageProcessors,
                                         List<ServerEventListener> eventListeners) {
        // message processor
        MessageProcessorManager messageProcessorManager = new MessageProcessorManager();
        serverMessageProcessors.forEach(messageProcessorManager::register);
        // event listener
        ChannelEvents channelEvents = new ChannelEvents(eventListeners);

        return new TutelaryServer(serverEndpointConfig, messageProcessorManager, channelEvents);
    }

}
