package com.tutelary.common;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ResponseMessage extends BaseMessage {

    protected Boolean status = true;

    protected String message;

    public void setErrorInfo(String errorMsg) {
        status = false;
        this.message = errorMsg;
    }

}
