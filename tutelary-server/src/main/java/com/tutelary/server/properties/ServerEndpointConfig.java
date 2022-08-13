package com.tutelary.server.properties;

import com.tutelary.common.constants.PersistentMannerConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tutelary.server")
@Data
public class ServerEndpointConfig {

    private int port = 9897;

    private int webPort = 9898;

    private String path = "/ws";

    private String webPath = "/ws";

    private int handshakeTimeout = 5000;

    private int bossLoopGroupThreads = 1;

    private int workerLoopGroupThreads = Runtime.getRuntime().availableProcessors();

    private int webBossLoopGroupThreads = 1;

    private int webWorkerLoopGroupThreads = Runtime.getRuntime().availableProcessors();

    private String persistent = PersistentMannerConstants.MEMORY;

}
