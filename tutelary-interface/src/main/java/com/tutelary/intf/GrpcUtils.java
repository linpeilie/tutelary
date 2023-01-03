package com.tutelary.intf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.tutelary.common.RequestMessage;
import com.tutelary.common.ResponseMessage;
import com.tutelary.grpc.lib.Payload;

import java.io.IOException;

public class GrpcUtils {

    public static Payload pack(ResponseMessage response) {
        try {
            Codec codec = ProtobufProxy.create(response.getClass());
            byte[] bytes = codec.encode(response);
            ByteString byteString = ByteString.copyFrom(bytes);
            return Payload.newBuilder()
                    .setBody(Any.newBuilder().setValue(byteString).build())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends RequestMessage> T unpack(Payload payload, Class<T> clazz) {
        Codec<T> codec = ProtobufProxy.create(clazz);
        try {
            T request = codec.decode(payload.getBody().getValue().toByteArray());
            return request;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
