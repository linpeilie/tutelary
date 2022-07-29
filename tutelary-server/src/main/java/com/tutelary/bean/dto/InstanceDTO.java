package com.tutelary.bean.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.tutelary.common.BaseMessage;

import com.tutelary.common.bean.dto.BaseDto;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstanceDTO extends BaseDto {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

    private long lastBeat = System.currentTimeMillis();

    private Channel channel;

    public void writeAndFlush(BaseMessage message) {
        if (channel.isActive()) {
            channel.writeAndFlush(message);
        }
    }

}
