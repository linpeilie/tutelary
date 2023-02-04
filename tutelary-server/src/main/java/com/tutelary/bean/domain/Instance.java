package com.tutelary.bean.domain;

import com.tutelary.common.BaseMessage;
import com.tutelary.common.bean.domain.BaseDomain;
import com.tutelary.common.enums.InstanceStateEnum;
import com.tutelary.remoting.api.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instance extends BaseDomain {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

    private InstanceStateEnum state;

    private List<String> inputArguments;

    private Map<String, String> systemProperties;

    private String classPath;

    private String libraryPath;

    private String vmVendor;

    private String vmName;

    private String vmVersion;

    private String jdkVersion;

    private LocalDateTime startTime;

    private Channel channel;

    private long lastBeat = System.currentTimeMillis();

    public void sendData(BaseMessage message) {
        if (channel.isConnected()) {
            channel.send(message);
        }
    }

}
