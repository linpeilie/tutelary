package com.tutelary.intf;

import com.tutelary.grpc.lib.Payload;
import com.tutelary.grpc.lib.RequestGrpc;
import io.grpc.stub.StreamObserver;

public class GrpcRequestAcceptor extends RequestGrpc.RequestImplBase {

    @Override
    public void request(Payload request, StreamObserver<Payload> responseObserver) {
        int type = request.getMetadata().getType();

    }
}
