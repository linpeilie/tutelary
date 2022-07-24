package com.tutelary.bean.entity;

import java.io.Serializable;
import java.util.Date;

import com.tutelary.common.BaseMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceEntity implements Serializable {

    private String instanceId;

    private String appName;

    private String ip;

    private Date registerDate;

    private long lastBeat = System.currentTimeMillis();

    private Channel channel;

    public void writeAndFlush(BaseMessage message) {
        if (channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

}
