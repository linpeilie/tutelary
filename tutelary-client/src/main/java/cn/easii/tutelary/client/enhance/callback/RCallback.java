package cn.easii.tutelary.client.enhance.callback;

import java.util.function.Consumer;

public class RCallback<T> {

    private final Consumer<T> resultConsumer;

    public RCallback(Consumer<T> resultConsumer) {
        this.resultConsumer = resultConsumer;
    }

    public void callback(T result) {
        resultConsumer.accept(result);
    }

}
