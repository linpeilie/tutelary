package com.tutelary.server.rpc;

import com.tutelary.grpc.lib.ClientRegisterRequest;
import com.tutelary.grpc.lib.ClientRegisterResponse;
import com.tutelary.grpc.lib.ClientRegisterServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ClientRegisterServiceImpl extends ClientRegisterServiceGrpc.ClientRegisterServiceImplBase {

    @Override
    public void register(ClientRegisterRequest request, StreamObserver<ClientRegisterResponse> responseObserver) {

    }
}
