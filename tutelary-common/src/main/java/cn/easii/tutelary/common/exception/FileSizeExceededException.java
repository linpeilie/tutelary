package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;

public class FileSizeExceededException extends BusinessException {
    public FileSizeExceededException() {
        super(CommonResponseCode.UPLOAD_FILE_SIZE_EXCEED);
    }
}
