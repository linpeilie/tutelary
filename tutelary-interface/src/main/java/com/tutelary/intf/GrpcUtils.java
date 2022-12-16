package com.tutelary.intf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.tutelary.grpc.lib.Payload;
import com.tutelary.intf.common.Request;
import com.tutelary.intf.common.Response;

import java.io.IOException;

public class GrpcUtils {

    public static Payload pack(Response response) {
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

    public static <T extends Request> T unpack(Payload payload, Class<T> clazz) {
        Codec<T> codec = ProtobufProxy.create(clazz);
        try {
            T request = codec.decode(payload.getBody().getValue().toByteArray());
            return request;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
