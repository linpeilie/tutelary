package cn.easii.tutelary.client.command.getstatic;

import cn.easii.tutelary.client.exception.ClassNotFoundException;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;
import cn.easii.tutelary.common.utils.ClassUtil;
import cn.easii.tutelary.message.command.param.GetStaticRequest;
import cn.easii.tutelary.message.command.result.GetStaticResponse;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.easii.tutelary.client.command.Command;
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
        Class<?> targetClass = ClassUtil.searchClass(inst, request.getQualifiedClassName());
        if (targetClass == null) {
            throw new ClassNotFoundException(request.getClassLoader(), request.getQualifiedClassName());
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
                request.getQualifiedClassName(),
                request.getField(), ExceptionUtil.stacktraceToString(e));
            commandResponse.failed("failed to get static value, exception message : " + e.getMessage());
        }
        return commandResponse;
    }

    @Override
    public void terminated() {

    }
}
