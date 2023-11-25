package cn.easii.tutelary.common.message;

import java.util.function.Consumer;

public interface MessageManager {

    <T> void publish(String topic, T message);

    <T> void subscribe(String topic, Class<T> clazz, Consumer<T> consumer);

}
