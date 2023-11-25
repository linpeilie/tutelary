package cn.easii.tutelary.config;

import cn.easii.tutelary.handler.MessageAcceptor;
import cn.easii.tutelary.processor.MessageProcessor;
import cn.easii.tutelary.processor.MessageProcessorManager;
import cn.easii.tutelary.remoting.TutelaryServer;
import cn.easii.tutelary.remoting.properties.ServerEndpointConfig;
import cn.easii.tutelary.remoting.api.ChannelHandler;
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
