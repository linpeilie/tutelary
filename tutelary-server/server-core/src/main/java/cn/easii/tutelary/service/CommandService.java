package cn.easii.tutelary.service;

import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.common.CommandRequest;

public interface CommandService {

    CommandTask createCommand(Integer commandCode, String instanceId, CommandRequest param);

}
