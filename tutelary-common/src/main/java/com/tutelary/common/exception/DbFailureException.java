package com.tutelary.common.exception;

import com.tutelary.common.constants.CommonResponseCode;

public class DbFailureException extends BaseException {
    public DbFailureException() {
        super(CommonResponseCode.DB_FAILURE);
    }
}
