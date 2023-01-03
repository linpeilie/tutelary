package com.tutelary.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RequestMessage extends BaseMessage {

    public void checkInput() {}

}
