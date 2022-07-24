package com.tutelary.session;

import com.tutelary.common.BaseMessage;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class Session implements Serializable {

    private final String sessionId;

    private final Channel channel;

    public Session(String sessionId, Channel channel) {
        this.sessionId = sessionId;
        this.channel = channel;
    }

    public void writeAndFlush(BaseMessage message) {
        if (channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

}
