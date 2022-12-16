package com.tutelary.intf.process;

import com.tutelary.grpc.lib.Payload;
import com.tutelary.intf.GrpcUtils;
import com.tutelary.intf.common.Request;
import com.tutelary.intf.common.Response;
import io.grpc.stub.StreamObserver;

public abstract class AbstractMessageProcess<Req extends Request, Res extends Response> implements MessageProcess<Req> {

    private final StreamObserver<Payload> observer;

    public AbstractMessageProcess(StreamObserver<Payload> observer) {
        this.observer = observer;
    }

    protected void next(Res response) {
        Payload payload = GrpcUtils.pack(response);
        observer.onNext(payload);
    }

    protected void complete() {
        observer.onCompleted();
    }
}
