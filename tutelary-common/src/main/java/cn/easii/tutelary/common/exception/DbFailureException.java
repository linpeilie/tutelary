package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class DbFailureException extends BaseException {
    public DbFailureException() {
        super(CommonResponseCode.DB_FAILURE);
    }
}
