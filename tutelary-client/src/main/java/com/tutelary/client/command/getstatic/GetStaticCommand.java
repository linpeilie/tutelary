package com.tutelary.client.command.getstatic;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.tutelary.client.command.Command;
import com.tutelary.client.exception.ClassNotFoundException;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.common.utils.ClassUtil;
import com.tutelary.message.command.param.GetStaticRequest;
import com.tutelary.message.command.result.GetStaticResponse;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class GetStaticCommand implements Command<GetStaticResponse> {

    private static final Log LOG = LogFactory.get(GetStaticCommand.class);

    private GetStaticRequest request;

    private Instrumentation inst;

    public GetStaticCommand(GetStaticRequest request, Instrumentation inst) {
        this.request = request;
        this.inst = inst;
    }

    @Override
    public GetStaticResponse execute() {
        Class<?> targetClass = ClassUtil.searchClass(inst, request.getClassName());
        if (targetClass == null) {
            throw new ClassNotFoundException(request.getClassLoader(), request.getClassName());
        }

        Optional<Field> fieldOptional = Arrays.stream(targetClass.getDeclaredFields())
            .filter(field -> Modifier.isStatic(field.getModifiers()) && field.getName().equals(request.getField()))
            .findFirst();

        GetStaticResponse commandResponse = new GetStaticResponse();
        if (!fieldOptional.isPresent()) {
            commandResponse.failed("no matched static field " + request.getField() + " was found");
            return commandResponse;
        }

        Field field = fieldOptional.get();

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        try {
            Object value = field.get(null);

            commandResponse.setValue(String.valueOf(value));
        } catch (IllegalAccessException e) {
            LOG.warn("getStatic: failed to get static value, class : {}, field : {}, error message : {}",
                request.getClassName(),
                request.getField(), ExceptionUtil.stacktraceToString(e));
            commandResponse.failed("failed to get static value, exception message : " + e.getMessage());
        }
        return commandResponse;
    }

    @Override
    public void terminated() {

    }
}
