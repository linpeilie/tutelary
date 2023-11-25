package cn.easii.tutelary.common.log.dialect.console;

import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;

/**
 * 利用System.out.println()打印日志
 *
 * @author Looly
 */
public class ConsoleLogFactory extends LogFactory {

    public ConsoleLogFactory() {
        super("Console Logging");
    }

    @Override
    public Log createLog(String name) {
        return new ConsoleLog(name);
    }

    @Override
    public Log createLog(Class<?> clazz) {
        return new ConsoleLog(clazz);
    }

}
