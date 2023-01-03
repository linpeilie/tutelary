package com.tutelary.intf.process;

import cn.hutool.core.util.TypeUtil;
import com.tutelary.common.RequestMessage;
import com.tutelary.grpc.lib.Payload;
import io.grpc.stub.StreamObserver;

import java.lang.reflect.Type;

public interface RequestHandler<Req extends RequestMessage> {

    void process(Req request, StreamObserver<Payload> observer);

    default Class<Req> getRequestClass() {
        Type[] typeArguments = TypeUtil.getTypeArguments(getClass());
        for (Type typeArgument : typeArguments) {
            if (RequestMessage.class.isAssignableFrom(TypeUtil.getClass(typeArgument))) {
                return (Class<Req>) TypeUtil.getClass(typeArgument);
            }
        }
        throw new RuntimeException("unknown request class");
    }

}
