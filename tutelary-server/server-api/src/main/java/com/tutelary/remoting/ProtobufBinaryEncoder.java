package com.tutelary.remoting;

import com.tutelary.common.ResponseMessage;
import com.tutelary.exception.ProtobufEncodeException;
import com.tutelary.remoting.netty.utils.ProtobufEncodeUtils;
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
