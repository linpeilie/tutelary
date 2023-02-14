package com.tutelary.remoting.api.exception;

import com.tutelary.common.exception.BaseException;
import com.tutelary.remoting.api.Channel;
import java.net.InetSocketAddress;

public class RemotingException extends BaseException {

    private InetSocketAddress localAddress;

    private InetSocketAddress remoteAddress;

    public RemotingException(Channel channel, String msg) {
        this(channel == null ? null : channel.getLocalAddress(), channel == null ? null : channel.getRemoteAddress(),
            msg
        );
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message) {
        super(message);
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public RemotingException(Channel channel, Throwable throwable) {
        this(channel == null ? null : channel.getLocalAddress(), channel == null ? null : channel.getRemoteAddress(),
            throwable
        );
    }

    public RemotingException(Channel channel, String errorMessage, Throwable throwable) {
        this(channel == null ? null : channel.getLocalAddress(), channel == null ? null : channel.getRemoteAddress(),
            errorMessage, throwable
        );
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, Throwable throwable) {
        super(throwable);
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message,
        Throwable throwable) {
        super(message, throwable);
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }
}
