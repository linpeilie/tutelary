package cn.easii.tutelary.remoting.api;

import java.io.IOException;

public interface Codec {

    byte[] encode(Object message) throws IOException;

    Object decode(byte[] bytes) throws IOException;

}
