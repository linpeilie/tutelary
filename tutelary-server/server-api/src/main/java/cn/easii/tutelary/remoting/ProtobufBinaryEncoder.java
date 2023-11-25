package cn.easii.tutelary.remoting;

import cn.easii.tutelary.common.ResponseMessage;
import cn.easii.tutelary.exception.ProtobufEncodeException;
import cn.easii.tutelary.remoting.netty.utils.ProtobufEncodeUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ProtobufBinaryEncoder implements Encoder.Binary<ResponseMessage> {

    @Override
    public void init(final EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public ByteBuffer encode(final ResponseMessage object) throws EncodeException {
        try {
            return ByteBuffer.wrap(ProtobufEncodeUtils.encode(object));
        } catch (IOException e) {
            throw new ProtobufEncodeException(e);
        }
    }
}
