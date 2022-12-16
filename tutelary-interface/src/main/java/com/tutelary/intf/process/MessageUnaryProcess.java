package com.tutelary.intf.process;

import com.tutelary.grpc.lib.Payload;
import com.tutelary.intf.common.Request;
import com.tutelary.intf.common.Response;
import io.grpc.stub.StreamObserver;

public abstract class MessageUnaryProcess<Req extends Request, Res extends Response> extends AbstractMessageProcess<Req, Res> {

    public MessageUnaryProcess(StreamObserver<Payload> observer) {
        super(observer);
    }

    @Override
    public void process(Req request) {
        Res res = process1(request);
        next(res);
        complete();
    }

    protected abstract Res process1(Req request);

}
