package com.tutelary.common.exception;

import com.tutelary.common.constants.CommonResponseCode;

public class FileSizeExceededException extends BusinessException {
    public FileSizeExceededException() {
        super(CommonResponseCode.UPLOAD_FILE_SIZE_EXCEED);
    }
}
