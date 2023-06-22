package com.tutelary.client.exception;

import com.tutelary.client.constants.EnhanceResponseCode;
import com.tutelary.common.exception.BaseException;

public class EnhanceNotAllowedException extends BaseException {

    public EnhanceNotAllowedException(EnhanceResponseCode responseCode, Object... args) {
        super(responseCode, args);
    }

}
