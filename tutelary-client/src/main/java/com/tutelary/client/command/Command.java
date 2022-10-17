package com.tutelary.client.command;

public interface Command<T> {

    /**
     * 执行命令
     */
    void execute(T param);

}
