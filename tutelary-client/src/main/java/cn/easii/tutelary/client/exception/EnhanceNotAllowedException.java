package cn.easii.tutelary.client.exception;

import cn.easii.tutelary.common.exception.BaseException;
import cn.easii.tutelary.client.constants.EnhanceResponseCode;

public class EnhanceNotAllowedException extends BaseException {

    public EnhanceNotAllowedException(EnhanceResponseCode responseCode, Object... args) {
        super(responseCode, args);
    }

}
