package cn.easii.tutelary.common.bean;

import cn.easii.tutelary.common.constants.ResponseCode;
import java.io.Serializable;
import lombok.Data;

@Data
public class R<T> implements Serializable {

    private static final Integer SUCCESS = 1;
    private static final Integer FAILED = 0;

    private Integer code;

    private String errorCode;

    private String message;

    private T data;

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(SUCCESS);
        r.setData(data);
        return r;
    }

    public static R<Void> success() {
        R<Void> r = new R<>();
        r.setCode(SUCCESS);
        return r;
    }

    public static R<Void> failure(ResponseCode responseCode) {
        return failure(responseCode.getCode(), responseCode.getMessage());
    }

    public static R<Void> failure(String errorCode, String message) {
        R<Void> r = new R<>();
        r.setCode(FAILED);
        r.setErrorCode(errorCode);
        r.setMessage(message);
        return r;
    }

}
