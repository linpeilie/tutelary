package com.tutelary.remoting.transport;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.tutelary.message.ErrorMessage;
import com.tutelary.remoting.api.Codec;
import com.tutelary.remoting.netty.codec.ProtobufCodec;

import cn.hutool.core.lang.Assert;

public class ProtobufCodecTest {

    @Test
    public void testCodec() throws IOException {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("error message");
        errorMessage.setLastCmd("1");


        Codec protobufCodec = new ProtobufCodec();
        byte[] bytes = protobufCodec.encode(errorMessage);
        ErrorMessage errorMessage1 = (ErrorMessage) protobufCodec.decode(bytes);
        Assert.equals(errorMessage, errorMessage1);
    }

}
