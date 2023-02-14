package com.tutelary.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tutelary.server")
@Data
public class ServerEndpointConfig {

    private String host = "0.0.0.0";

    private int port = 9897;

}
