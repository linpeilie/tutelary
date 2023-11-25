package cn.easii.tutelary.common.utils;

import cn.easii.tutelary.common.function.InnerFunction;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;

public class ThrowableUtil {

    private static final Log LOG = LogFactory.get();

    public static void safeExec(InnerFunction function) {
        try {
            function.exec();
        } catch (Throwable e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    public static void ignoringExceptionsExecute(InnerFunction function) {
        try {
            function.exec();
        } catch (Throwable e) {
            // ignore
        }
    }

}
