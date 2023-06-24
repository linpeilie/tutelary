package com.tutelary.config;

import com.tutelary.handler.MessageAcceptor;
import com.tutelary.processor.MessageProcessor;
import com.tutelary.processor.MessageProcessorManager;
import com.tutelary.remoting.TutelaryServer;
import com.tutelary.remoting.api.ChannelHandler;
import com.tutelary.remoting.properties.ServerEndpointConfig;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ServerEndpointConfig.class)
public class ServerConfig {

    @Bean(name = "messageAcceptor")
    public MessageAcceptor messageAcceptor() {
        return new MessageAcceptor();
    }

    @Bean(destroyMethod = "destroy")
    public TutelaryServer tutelaryServer(ServerEndpointConfig serverEndpointConfig,
        List<MessageProcessor> messageProcessors,
        List<ChannelHandler> channelHandlers) {
        messageProcessors.forEach(MessageProcessorManager::register);
        return new TutelaryServer(serverEndpointConfig, channelHandlers);
    }

}
