package cn.easii.tutelary.common.exception;

import cn.easii.tutelary.common.constants.CommonResponseCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class ExceptionConverter {

    public static BaseException transException(Throwable e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        }

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            String errorMessage = exception.getAllErrors().get(0).getDefaultMessage();
            return new IllegalArgumentException(String.format(CommonResponseCode.ILLEGAL_ARGUMENT.getMessage(),
                    errorMessage));
        }

        if (e instanceof MaxUploadSizeExceededException) {
            return new FileSizeExceededException();
        }


        return new BusinessException(CommonResponseCode.SERVER_ERROR);
    }

}
