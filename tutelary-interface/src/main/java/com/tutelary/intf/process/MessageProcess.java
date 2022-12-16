package com.tutelary.intf.process;

import cn.hutool.core.util.TypeUtil;
import com.tutelary.intf.common.Request;

import java.lang.reflect.Type;

public interface MessageProcess<Req extends Request> {

    void process(Req request);

    default Class<Req> getRequestClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (Request.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<Req>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown request class");
    }

}
