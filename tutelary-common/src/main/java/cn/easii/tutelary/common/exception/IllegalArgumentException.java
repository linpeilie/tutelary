package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class IllegalArgumentException extends BusinessException {
    public IllegalArgumentException(String errorMessage) {
        super(CommonResponseCode.ILLEGAL_ARGUMENT, errorMessage);
    }
}
