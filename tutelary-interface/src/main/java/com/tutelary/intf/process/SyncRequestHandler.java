package com.tutelary.intf.process;

import com.tutelary.common.RequestMessage;
import com.tutelary.common.ResponseMessage;
import com.tutelary.grpc.lib.Payload;
import com.tutelary.intf.GrpcUtils;
import io.grpc.stub.StreamObserver;

public abstract class SyncRequestHandler<Req extends RequestMessage, Res extends ResponseMessage> implements RequestHandler<Req> {
    @Override
    public void process(Req request, StreamObserver<Payload> observer) {
        Res res = process1(request);
        observer.onNext(GrpcUtils.pack(res));
        observer.onCompleted();
    }

    public abstract Res process1(Req request);

}
