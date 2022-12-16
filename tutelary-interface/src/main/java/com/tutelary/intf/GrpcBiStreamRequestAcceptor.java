package com.tutelary.intf;

import com.tutelary.grpc.lib.BiStreamRequestGrpc;
import com.tutelary.grpc.lib.Payload;
import io.grpc.stub.StreamObserver;

public class GrpcBiStreamRequestAcceptor extends BiStreamRequestGrpc.BiStreamRequestImplBase {

    @Override
    public void requestBiStream(Payload request, StreamObserver<Payload> responseObserver) {

    }

}
