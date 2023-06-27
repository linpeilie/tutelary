package com.tutelary.listener;

import com.tutelary.SessionStore;
import com.tutelary.bean.domain.CommandTask;
import com.tutelary.common.constants.Constants;
import com.tutelary.common.message.MessageManager;
import com.tutelary.dao.CommandTaskDAO;
import com.tutelary.message.CommandExecuteResponse;
import com.tutelary.utils.AuthHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskCallbackListener implements ApplicationRunner, Ordered {

    private final MessageManager messageManager;

    private final SessionStore sessionStore;

    private final CommandTaskDAO commandTaskDAO;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        messageManager.subscribe(Constants.Topic.TASK_CALLBACK_NOTIFY_USER,
            CommandExecuteResponse.class,
            response -> {
                final CommandTask task = commandTaskDAO.getByTaskId(response.getTaskId());
                if (task == null) {
                    return;
                }
                final String token = AuthHelper.getTokenByUserId(task.getCreateUserId());
                sessionStore.sendMessage(token, response);
            });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
