package cn.easii.tutelary.config;

import cn.easii.tutelary.common.bean.R;
import cn.easii.tutelary.common.constants.CommonResponseCode;
import cn.easii.tutelary.common.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler(NotLoginException.class)
    public R<Void> notLoginExceptionHandler(NotLoginException e) {
        return R.failure(CommonResponseCode.NO_LOGIN);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<Void> maxUploadSizeException(MaxUploadSizeExceededException e) {
        return R.failure(CommonResponseCode.UPLOAD_FILE_SIZE_EXCEED);
    }

    @ExceptionHandler(Exception.class)
    public R<Void> uncaughtException(Exception e) {
        log.error("未捕获的异常信息", e);
        return R.failure(CommonResponseCode.SERVER_ERROR);
    }

}
