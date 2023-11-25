package cn.easii.tutelary.client.command;

public interface Command<Result> {

    /**
     * 执行命令
     */
    Result execute();

    /**
     * 中断命令
     */
    void terminated();

}
