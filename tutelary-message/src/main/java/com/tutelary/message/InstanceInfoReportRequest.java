package com.tutelary.message;

import com.tutelary.MessageCmd;
import com.tutelary.annotation.Message;
import com.tutelary.common.RequestMessage;
import com.tutelary.common.utils.ParameterUtils;
import com.tutelary.message.command.result.Overview;
import lombok.Data;

@Data
@Message(cmd = MessageCmd.INSTANCE_INFO_REPORT)
public class InstanceInfoReportRequest extends RequestMessage {

    private String instanceId;

    private Overview overview;

    /**
     * 统计当前时间戳
     */
    private long currentTime;

    @Override
    public void checkInput() {
        ParameterUtils.notBlank(instanceId, "instance id cannot be blank");
        ParameterUtils.nonNull(overview, "instance overview info cannot be null");
    }
}
