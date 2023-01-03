package com.tutelary.server.handler;

import com.tutelary.grpc.lib.Payload;
import com.tutelary.intf.process.SyncRequestHandler;
import com.tutelary.message.ClientRegisterRequest;
import com.tutelary.message.ClientRegisterResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class ClientRegisterRequestHandler extends SyncRequestHandler<ClientRegisterRequest, ClientRegisterResponse> {

    @Override
    public ClientRegisterResponse process1(ClientRegisterRequest request) {
        return null;
    }
}
