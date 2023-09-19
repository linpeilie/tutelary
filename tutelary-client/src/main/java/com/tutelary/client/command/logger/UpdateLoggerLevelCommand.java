package com.tutelary.client.command.logger;

import com.google.common.collect.Multimap;
import com.tutelary.client.command.Command;
import com.tutelary.client.exception.ClassLoaderNotFoundException;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.command.param.UpdateLoggerLevelRequest;
import com.tutelary.message.command.result.UpdateLoggerLevelResponse;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

public class UpdateLoggerLevelCommand extends AbstractLoggerCommand implements Command<UpdateLoggerLevelResponse> {

    private static final Log log = LogFactory.get(UpdateLoggerLevelCommand.class);

    private final UpdateLoggerLevelRequest request;

    public UpdateLoggerLevelCommand(UpdateLoggerLevelRequest request, Instrumentation inst) {
        super(inst);
        this.request = request;
    }

    @Override
    public UpdateLoggerLevelResponse execute() {
        Multimap<ClassLoader, LoggerType> classLoaderLoggerTypeMap = loadClassLoaderLoggers();
        Optional<ClassLoader> optional = classLoaderLoggerTypeMap.keys().stream()
            .filter(classLoader -> ClassUtil.classLoaderHash(classLoader).equals(request.getClassLoaderHashCode()))
            .findFirst();
        UpdateLoggerLevelResponse updateLoggerLevelResponse = new UpdateLoggerLevelResponse();
        if (optional.isPresent()) {
            ClassLoader classLoader = optional.get();
            Collection<LoggerType> loggerTypes = classLoaderLoggerTypeMap.get(classLoader);
            loggerTypes.forEach(loggerType -> {
                String loggerHelperClassName = loggerHelperMap.get(loggerType);
                Boolean success = updateLevel(classLoader, loggerHelperClassName);
                if (success) {
                    updateLoggerLevelResponse.setSuccess(success);
                }
            });
        } else {
            log.error("class loader not found, request : {}", request);
            throw new ClassLoaderNotFoundException(request.getClassLoaderHashCode());
        }
        return updateLoggerLevelResponse;
    }

    private Boolean updateLevel(ClassLoader classLoader, String loggerHelperClassName) {
        try {
            Class<?> loggerHelperClass = classLoader.loadClass(loggerHelperClassName);
            Method updateLevelMethod = loggerHelperClass.getMethod("updateLevel", String.class, String.class);
            return (Boolean) updateLevelMethod.invoke(null, request.getName(), request.getLevel());
        } catch (ClassNotFoundException e) {
            log.error("[UpdateLoggerLevelCommand] logger helper class not found, class name : {}",
                loggerHelperClassName);
        } catch (InvocationTargetException e) {
            log.error("[UpdateLoggerLevelCommand] exception occurred during invoking updateLevel method", e);
        } catch (NoSuchMethodException e) {
            log.error("[UpdateLoggerLevelCommand] there is not updateLevel method, LoggerHelper name : {}",
                loggerHelperClassName);
        } catch (IllegalAccessException e) {
            log.error("[UpdateLoggerLevelCommand] the updateLevel method access exception, LoggerHelper name : {}",
                loggerHelperClassName);
        }
        return Boolean.FALSE;
    }

    @Override
    public void terminated() {

    }
}
