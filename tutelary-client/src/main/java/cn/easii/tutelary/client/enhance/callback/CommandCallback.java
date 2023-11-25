package cn.easii.tutelary.client.enhance.callback;

import cn.easii.tutelary.common.CommandResponse;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandCallback {

    private final RCallback<CommandResponse> callback;

    private final int limit;

    private final AtomicInteger executeCnd = new AtomicInteger(0);

    public CommandCallback(int limit) {
        this.limit = limit;
        this.callback = new RCallback<>((commandResult -> {
            int i = executeCnd.incrementAndGet();
            if (i > limit) {
                System.out.println("任务结束");
            } else {

            }
        }));
    }

}
