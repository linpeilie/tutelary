package com.tutelary.common;

import cn.hutool.core.lang.UUID;
import lombok.Data;

@Data
public abstract class RequestMessage extends BaseMessage {

    private final String requestId;

    public RequestMessage() {
        requestId = UUID.fastUUID().toString(true);
    }

    public void checkInput() {}

}
