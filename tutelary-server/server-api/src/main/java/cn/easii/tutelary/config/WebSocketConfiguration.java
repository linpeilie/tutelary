package cn.easii.tutelary.config;

import cn.easii.tutelary.remoting.interceptor.AuthHandshakeInterceptor;
import cn.easii.tutelary.remoting.TutelaryWebServerHandler;
import cn.easii.tutelary.remoting.interceptor.SetAttributesInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Value("${api-prefix:/api}")
    private String apiPrefix;

    private final AuthHandshakeInterceptor authHandshakeInterceptor;
    private final SetAttributesInterceptor setAttributesInterceptor;
    private final TutelaryWebServerHandler webServerHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webServerHandler, apiPrefix + "/ws")
            .setAllowedOrigins("*")
            .addInterceptors(authHandshakeInterceptor, setAttributesInterceptor);
    }
}
