package com.tutelary.common;

import lombok.Data;

@Data
public abstract class ResponseMessage extends BaseMessage {

    protected Boolean status = true;

    protected String message;

}
