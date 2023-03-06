package com.tutelary.common.bean;

import java.io.Serializable;
import lombok.Data;

@Data
public class R<T> implements Serializable {

    private static final Integer SUCCESS = 1;
    private static final Integer FAILED = 0;

    private Integer code;

    private String message;

    private T data;

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(SUCCESS);
        r.setData(data);
        return r;
    }

    public static R<?> success() {
        R<?> r = new R<>();
        r.setCode(SUCCESS);
        return r;
    }

    public static R<?> failure(String message) {
        R<?> r = new R<>();
        r.setCode(FAILED);
        r.setMessage(message);
        return r;
    }

}
