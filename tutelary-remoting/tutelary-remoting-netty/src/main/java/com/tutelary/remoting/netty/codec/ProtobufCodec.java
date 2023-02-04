package com.tutelary.remoting.netty.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.common.collect.ImmutableMap;
import com.tutelary.annotation.Message;
import com.tutelary.common.BaseMessage;
import com.tutelary.common.utils.Asserts;
import com.tutelary.exception.UnknownCommandException;
import com.tutelary.remoting.api.Codec;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.ClassUtil;

@SuppressWarnings("unchecked")
public class ProtobufCodec implements Codec {

    private static final ImmutableMap<Byte, Class<? extends BaseMessage>> MESSAGE_MAP;

    static {
        Map<Byte, Class<? extends BaseMessage>> messageMap = new HashMap<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation("com.tutelary.message", Message.class);
        for (Class<?> aClass : classes) {
            Message messageAnnotation = aClass.getAnnotation(Message.class);
            byte cmd = messageAnnotation.cmd();
            messageMap.put(cmd, (Class<? extends BaseMessage>) aClass);
        }
        MESSAGE_MAP = ImmutableMap.copyOf(messageMap);
    }

    @Override
    public byte[] encode(Object message) throws IOException {
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

    @Override
    public Object decode(byte[] bytes) throws IOException {
        byte cmd = bytes[0];
        Class<? extends BaseMessage> messageClass = MESSAGE_MAP.get(cmd);
        Asserts.notNull(messageClass, () -> new UnknownCommandException(cmd));
        byte[] lengthBytes = ArrayUtil.sub(bytes, 1, 5);
        System.arraycopy(bytes, 1, lengthBytes, 0, 4);
        int length = ByteUtil.bytesToInt(lengthBytes);
        byte[] realBytes = ArrayUtil.sub(bytes, 5, length);
        com.baidu.bjf.remoting.protobuf.Codec<? extends BaseMessage> codec = ProtobufProxy.create(messageClass);
        return codec.decode(realBytes);
    }
}
