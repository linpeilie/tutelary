package cn.easii.tutelary.remoting.transport;

import cn.easii.tutelary.message.ErrorMessage;
import cn.hutool.core.lang.Assert;
import cn.easii.tutelary.remoting.api.Codec;
import cn.easii.tutelary.remoting.netty.codec.ProtobufCodec;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class ProtobufCodecTest {

    @Test
    public void testCodec() throws IOException {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage("error message");

        Codec protobufCodec = new ProtobufCodec();
        byte[] bytes = protobufCodec.encode(errorMessage);
        ErrorMessage errorMessage1 = (ErrorMessage) protobufCodec.decode(bytes);
        Assert.equals(errorMessage, errorMessage1);
    }

}
