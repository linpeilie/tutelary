package cn.easii.tutelary.remoting.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tutelary.server")
@Data
public class ServerEndpointConfig {

    private String host = "0.0.0.0";

    private int port = 9897;

    private int webPort = 9997;

}
