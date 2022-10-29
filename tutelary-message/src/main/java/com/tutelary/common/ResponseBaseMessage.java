package com.tutelary.common;

import lombok.Data;

@Data
public abstract class ResponseBaseMessage extends BaseMessage {

    protected Boolean status = true;

    protected String message;

}
