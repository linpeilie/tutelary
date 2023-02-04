package com.tutelary.common.extension;

import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class ExtensionExecutor {

    private static final Log LOG = LogFactory.get(ExtensionExecutor.class);

    public <R, T> R execute(Class<T> targetClz, int commandCode, Function<T, R> exeFunction) {
        T extension = locateExtension(targetClz, commandCode);
        return exeFunction.apply(extension);
    }

    public <T> void executeVoid(Class<T> targetClz, int commandCode, Consumer<T> exeFunction) {
        T extension = locateExtension(targetClz, commandCode);
        exeFunction.accept(extension);
    }

    public <T> T getExtension(Class<T> targetClz, int commandCode) {
        return locateExtension(targetClz, commandCode);
    }

    private <Ext> Ext locateExtension(Class<Ext> targetClz, int commandCode) {
        LOG.debug("commandCode in locateExtension is : {}", commandCode);

        Ext extension = locate(targetClz, commandCode);
        if (extension != null) {
            return extension;
        }

        throw new RuntimeException("Can not find extension with ExtensionPoint : "
                + targetClz + " commandCode : " + commandCode);
    }

    private <Ext> Ext locate(Class<Ext> targetClz, int commandCode) {
        return (Ext) ExtensionRepository.get(new ExtensionCoordinate(targetClz, commandCode));
    }


}
