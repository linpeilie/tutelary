package com.tutelary.remoting;

import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TutelaryWebServerConfig extends ServerEndpointConfig.Configurator{

    @Override
    public void modifyHandshake(final ServerEndpointConfig sec,
        final HandshakeRequest request,
        final HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        final Map<String, List<String>> headers = request.getHeaders();
        log.info("headers : {}", headers);
        final URI requestURI = request.getRequestURI();
        log.info("requestURI : {}", requestURI);
        final Map<String, List<String>> parameterMap = request.getParameterMap();
        log.info("parameterMap : {}", parameterMap);
        final String queryString = request.getQueryString();
        log.info("queryString : {}", queryString);
    }
}
