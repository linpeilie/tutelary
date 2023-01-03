package com.tutelary.intf;

import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.ImmutableMap;
import com.tutelary.common.RequestMessage;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.grpc.lib.Payload;
import com.tutelary.grpc.lib.RequestGrpc;
import com.tutelary.intf.process.RequestHandler;
import com.tutelary.message.ErrorMessage;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GrpcRequestAcceptor extends RequestGrpc.RequestImplBase {
    @Override
    public StreamObserver<Payload> request(StreamObserver<Payload> responseObserver) {

        return new StreamObserver<Payload>() {
            @Override
            public void onNext(Payload payload) {
                new Thread(() -> {
                    for (int i = 0; i < 20; i++) {
                        responseObserver.onNext(payload);
                        ThreadUtil.safeSleep(400);
                    }
                }).start();
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("on error", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("on complete");
            }
        };
    }
}
