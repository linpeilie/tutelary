package com.tutelary.common.message;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessageManager implements MessageManager {

    private final RedissonClient REDISSON_CLIENT;

    @Override
    public <T> void publish(final String topic, final T message) {
        REDISSON_CLIENT.getTopic(topic).publish(message);
    }

    @Override
    public <T> void subscribe(final String topic, final Class<T> clazz, final Consumer<T> consumer) {
        REDISSON_CLIENT.getTopic(topic).addListener(clazz, (charSequence, t) -> consumer.accept(t));
    }
}
