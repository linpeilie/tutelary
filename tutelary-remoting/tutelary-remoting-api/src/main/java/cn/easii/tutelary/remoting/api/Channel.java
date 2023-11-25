package cn.easii.tutelary.remoting.api;

import java.net.InetSocketAddress;

public interface Channel extends Endpoint {

    boolean isConnected();

    InetSocketAddress getRemoteAddress();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    void removeAttribute(String key);

}
