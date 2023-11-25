package cn.easii.tutelary.remoting.api.exception;

import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.remoting.api.constants.RemotingResponseCode;

public class RemotingException extends BaseException {

    public RemotingException(RemotingResponseCode remotingResponseCode, Object... args) {
        super(remotingResponseCode, args);
    }

    public RemotingException(Throwable cause, RemotingResponseCode responseCode, Object... args) {
        super(cause, responseCode, args);
    }

}
