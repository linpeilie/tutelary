package com.tutelary.service;

import com.tutelary.bean.domain.CommandTask;
import com.tutelary.common.CommandRequest;

public interface CommandService {

    CommandTask createCommand(Integer commandCode, String instanceId, CommandRequest param);

}
