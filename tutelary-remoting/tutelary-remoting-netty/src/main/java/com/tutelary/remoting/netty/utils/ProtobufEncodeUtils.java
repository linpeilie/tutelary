package com.tutelary.remoting.netty.utils;

import cn.hutool.core.util.ByteUtil;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.tutelary.annotation.Message;
import com.tutelary.common.BaseMessage;
import java.io.IOException;

public class ProtobufEncodeUtils {

    public static byte[] encode(Object message) throws IOException {
        Class<BaseMessage> clazz = (Class<BaseMessage>) message.getClass();
        com.baidu.bjf.remoting.protobuf.Codec<BaseMessage> codec = ProtobufProxy.create(clazz);
        byte[] bytes = codec.encode((BaseMessage) message);
        int length = bytes.length;
        byte[] lengthBytes = ByteUtil.intToBytes(length);
        Message messageAnnotation = clazz.getAnnotation(Message.class);
        byte[] result = new byte[length + 5];
        result[0] = messageAnnotation.cmd();
        System.arraycopy(lengthBytes, 0, result, 2, lengthBytes.length);
        System.arraycopy(bytes, 0, result, 5, length);
        return result;
    }

}
