package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class SystemBusyException extends BaseException {
    public SystemBusyException() {
        super(CommonResponseCode.SERVER_BUSY);
    }
}
