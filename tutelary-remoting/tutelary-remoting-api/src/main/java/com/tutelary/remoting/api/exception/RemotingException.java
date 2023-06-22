package com.tutelary.remoting.api.exception;

import com.tutelary.common.exception.BaseException;
import com.tutelary.remoting.api.Channel;
import com.tutelary.remoting.api.constants.RemotingResponseCode;
import java.net.InetSocketAddress;

public class RemotingException extends BaseException {

    public RemotingException(RemotingResponseCode remotingResponseCode, Object... args) {
        super(remotingResponseCode, args);
    }

    public RemotingException(Throwable cause, RemotingResponseCode responseCode, Object... args) {
        super(cause, responseCode, args);
    }

}
