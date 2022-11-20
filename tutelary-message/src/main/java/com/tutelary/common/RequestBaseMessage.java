package com.tutelary.common;

import cn.hutool.core.lang.UUID;
import lombok.Data;

@Data
public abstract class RequestBaseMessage extends BaseMessage {

    public void checkInput() {}

}
