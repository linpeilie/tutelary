package cn.easii.tutelary.message;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.message.command.result.Overview;
import cn.easii.tutelary.MessageCmd;
import cn.easii.tutelary.common.RequestMessage;
import cn.easii.tutelary.common.utils.ParameterUtils;
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
