package com.tutelary.server.properties;

import com.tutelary.common.constants.PersistentMannerConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tutelary.server")
@Data
public class ServerEndpointConfig {

    private String host = "0.0.0.0";

    private int port = 9897;

    private String path = "/ws";

    private int handshakeTimeout = 5000;

    private int bossLoopGroupThreads = 1;

    private int workerLoopGroupThreads = Runtime.getRuntime().availableProcessors();

    private String persistent = PersistentMannerConstants.MEMORY;

}
