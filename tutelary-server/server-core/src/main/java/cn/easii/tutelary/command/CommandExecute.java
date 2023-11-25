package cn.easii.tutelary.command;

import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.common.CommandRequest;
import cn.easii.tutelary.common.CommandResponse;
import cn.easii.tutelary.common.extension.ExtensionPointI;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.message.CommandExecuteResponse;
import cn.easii.tutelary.remoting.api.Channel;

public interface CommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse>
    extends ExtensionPointI {

    CommandTask createCommand(String instanceId, PARAM request);

    void callResult(Channel channel, CommandExecuteResponse response);

    Integer commandCode();

    default Class<PARAM> getParamClass() {
        return ClassUtil.getGenericsBySuperClass(getClass(), CommandRequest.class);
    }

}
