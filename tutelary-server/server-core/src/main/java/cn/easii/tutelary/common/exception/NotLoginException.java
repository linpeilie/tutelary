package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class NotLoginException extends BaseException {
    public NotLoginException() {
        super(CommonResponseCode.NO_LOGIN);
    }
}
