package cn.easii.tutelary.command;

import com.google.common.collect.EvictingQueue;
import cn.easii.tutelary.InstanceManager;
import cn.easii.tutelary.bean.domain.Instance;
import cn.easii.tutelary.message.CommandCancelRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelTaskBiz {

    private static final int DEFAULT_MAX_CAPACITY = 256;

    private static final EvictingQueue<String> tasks = EvictingQueue.create(DEFAULT_MAX_CAPACITY);

    private final InstanceManager instanceManager;

    public void cancelTask(String instanceId, String taskId) {
        if (tasks.contains(taskId)) {
            return;
        }
        synchronized (tasks) {
            if (tasks.contains(taskId)) {
                return;
            }
            tasks.offer(taskId);
        }

        final Optional<Instance> instanceOptional = instanceManager.getInstance(instanceId);
        instanceOptional.ifPresent(instance -> {
            final CommandCancelRequest commandCancelRequest = new CommandCancelRequest();
            commandCancelRequest.setTaskId(taskId);
            instance.sendData(commandCancelRequest);
        });
    }

}
