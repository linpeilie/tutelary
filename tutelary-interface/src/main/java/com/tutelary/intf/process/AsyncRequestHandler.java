package com.tutelary.intf.process;

import com.tutelary.common.RequestMessage;
import com.tutelary.common.ResponseMessage;
import com.tutelary.grpc.lib.Payload;
import io.grpc.stub.StreamObserver;

public class AsyncRequestHandler<Req extends RequestMessage, Res extends ResponseMessage> implements RequestHandler<Req> {
    @Override
    public void process(Req request, StreamObserver<Payload> observer) {

    }
}
