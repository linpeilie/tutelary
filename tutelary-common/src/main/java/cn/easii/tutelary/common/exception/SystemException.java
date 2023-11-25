package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class SystemException extends BaseException {
    public SystemException() {
        super(CommonResponseCode.SERVER_ERROR);
    }
}
