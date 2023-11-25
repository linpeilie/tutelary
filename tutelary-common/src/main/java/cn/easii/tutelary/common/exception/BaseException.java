package cn.easii.tutelary.common.exception;

import cn.hutool.core.util.ArrayUtil;
import cn.easii.tutelary.common.constants.ResponseCode;
import cn.easii.tutelary.common.log.Log;
import cn.easii.tutelary.common.log.LogFactory;

public abstract class BaseException extends RuntimeException {

    private static final Log logger = LogFactory.get(BaseException.class);

    private String errorCode;
    private String errorMessage;

    private static String formatMessage(ResponseCode responseCode, Object... codeMessageArgs) {
        if (responseCode.getArgs() > 0) {
            if (codeMessageArgs == null || codeMessageArgs.length != responseCode.getArgs()) {
                logger.error(
                    "response code : {}, message template : {}, args : {}, the number of parameters is inconsistent, with parameters : {}",
                    responseCode.getCode(), responseCode.getMessage(), responseCode.getArgs(),
                    ArrayUtil.toString(codeMessageArgs));
                return responseCode.getMessage();
            } else {
                return String.format(responseCode.getMessage(), codeMessageArgs);
            }
        } else {
            return responseCode.getMessage();
        }
    }

    public BaseException(ResponseCode responseCode, Object... codeMessageArgs) {
        super(formatMessage(responseCode, codeMessageArgs));
        this.errorCode = responseCode.getCode();
        this.errorMessage = formatMessage(responseCode, codeMessageArgs);
    }

    public BaseException(Throwable cause, ResponseCode responseCode, Object... messageArgs) {
        super(formatMessage(responseCode, messageArgs), cause);
        this.errorCode = responseCode.getCode();
        this.errorMessage = formatMessage(responseCode, messageArgs);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
