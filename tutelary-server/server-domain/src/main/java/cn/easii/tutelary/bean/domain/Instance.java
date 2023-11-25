package cn.easii.tutelary.bean.domain;

import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.domain.BaseDomain;
import cn.easii.tutelary.enums.InstanceStateEnum;
import cn.easii.tutelary.remoting.api.Channel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
