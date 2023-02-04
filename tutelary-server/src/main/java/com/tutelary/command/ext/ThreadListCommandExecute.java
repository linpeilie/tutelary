package com.tutelary.command.ext;

import cn.hutool.core.util.StrUtil;
import com.tutelary.command.AbstractCommandExecute;
import com.tutelary.common.extension.Extension;
import com.tutelary.constants.CommandConstants;
import com.tutelary.constants.CommandEnum;
import com.tutelary.message.command.param.ThreadListRequest;
import com.tutelary.message.command.result.ThreadList;
import org.springframework.stereotype.Component;

@Component
@Extension(commandCode = CommandConstants.threadList)
public class ThreadListCommandExecute extends AbstractCommandExecute<ThreadListRequest, ThreadList> {

    @Override
    public Integer commandCode() {
        return CommandEnum.TUTELARY_THREAD_LIST.getCommandCode();
    }

}
