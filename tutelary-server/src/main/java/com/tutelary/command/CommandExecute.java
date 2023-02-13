package com.tutelary.command;

import cn.hutool.core.util.TypeUtil;
import com.tutelary.common.CommandRequest;
import com.tutelary.common.CommandResponse;
import com.tutelary.common.extension.ExtensionPointI;

import java.lang.reflect.Type;

public interface CommandExecute<PARAM extends CommandRequest, RESPONSE extends CommandResponse> extends ExtensionPointI {

    void createCommand(String instanceId, PARAM request);

    void callResult(RESPONSE response);

    Integer commandCode();

    default Class<PARAM> getParamClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (CommandRequest.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<PARAM>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown param class");
    }

}
