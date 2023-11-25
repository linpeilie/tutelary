package cn.easii.tutelary.remoting.netty.codec;

import cn.easii.tutelary.annotation.Message;
import cn.easii.tutelary.common.BaseMessage;
import cn.easii.tutelary.common.utils.Asserts;
import cn.easii.tutelary.exception.UnknownCommandException;
import cn.easii.tutelary.remoting.netty.utils.ProtobufEncodeUtils;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.ClassUtil;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.common.collect.ImmutableMap;
import cn.easii.tutelary.remoting.api.Codec;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        return ProtobufEncodeUtils.encode(message);
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
