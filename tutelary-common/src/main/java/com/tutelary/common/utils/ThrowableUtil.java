package com.tutelary.common.utils;

import com.tutelary.common.function.InnerFunction;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;

public class ThrowableUtil {

    private static final Log LOG = LogFactory.get();

    public static void safeExec(InnerFunction function) {
        try {
            function.exec();
        } catch (Throwable e) {
            LOG.warn(e.getMessage(), e);
        }
    }

}
