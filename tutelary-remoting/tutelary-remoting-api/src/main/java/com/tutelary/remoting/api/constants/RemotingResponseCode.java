package com.tutelary.remoting.api.constants;

import com.tutelary.common.constants.BusinessResponseCode;
import com.tutelary.common.constants.ResponseCode;
import lombok.Getter;

@Getter
public enum RemotingResponseCode implements ResponseCode {

    OPEN_CLIENT_FAILURE("Failed to start %s --- %s connect to the server %s, cause %s", 4),
    CONNECT_SERVER_FAILURE("Failed to start %s --- %s connect to the server %s, cause %s", 4),
    RECONNECT_SERVER_FAILURE("Failed connect to server %s from %s, cause : Connect wait timeout : %sms.", 3),
    CONNECT_SERVER_UNCAUGHT_EXCEPTION("Failed connect to server %s from %s, cause : %s", 3),
    CONNECT_SERVER_TIMEOUT("client(address : %s) failed to connect to server %s, client-side timeout %sms (elapsed: %sms) from client : %s", 5),
    SEND_FAILURE_DUE_TO_CHANNEL_CLOSED("Failed to send message, cause: Channel closed. channel : %s -> %s", 2),
    CLIENT_SEND_MESSAGE_FAILURE_DUE_TO_CHANNEL_CLOSED("message can not send, because channel is closed. endpoint context : %s", 1),
    SEND_MESSAGE_UNCAUGHT_EXCEPTION("Failed to send message to %s, cause : %s", 2),
    SERVER_OPEN_UNCAUGHT_EXCEPTION("Failed to bind %s on %s, cause : %s", 3)
    ;

    private final String code;
    private final String message;
    private final int args;

    RemotingResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.REMOTING.getCodePrefix());
        this.message = message;
        this.args = args;
    }

}
