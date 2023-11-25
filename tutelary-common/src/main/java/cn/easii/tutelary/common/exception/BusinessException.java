package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.ResponseCode;

public class BusinessException extends BaseException {
    public BusinessException(ResponseCode responseCode, Object... codeMessageArgs) {
        super(responseCode, codeMessageArgs);
    }
}
