package cn.easii.tutelary.remoting.api;

import cn.easii.tutelary.common.constants.TutelaryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络端点上下文
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndpointContext {

    /**
     * 协议
     */
    private String protocol;

    /**
     * 连接地址
     */
    private String host;

    /**
     * 连接端口
     */
    private int port;

    /**
     * 连接超时时间
     */
    @Builder.Default
    private int connectionTimeout = TutelaryConstants.DEFAULT_CONNECTION_TIMEOUT;

    /**
     * 心跳间隔
     */
    @Builder.Default
    private int heartbeatInterval = TutelaryConstants.DEFAULT_HEARTBEAT_INTERVAL;

    public String getAddress() {
        return port <= 0 ? host : host + ":" + port;
    }

}
