package cn.easii.tutelary.listener;

import cn.easii.tutelary.SessionStore;
import cn.easii.tutelary.bean.domain.CommandTask;
import cn.easii.tutelary.common.constants.Constants;
import cn.easii.tutelary.common.message.MessageManager;
import cn.easii.tutelary.dao.CommandTaskDAO;
import cn.easii.tutelary.message.CommandExecuteResponse;
import cn.easii.tutelary.utils.AuthHelper;
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
                sessionStore.sendMessage(task.getCreateUserId(), response);
            });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
