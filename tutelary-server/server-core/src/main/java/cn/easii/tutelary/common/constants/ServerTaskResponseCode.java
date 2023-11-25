package cn.easii.tutelary.common.constants;

import lombok.Getter;

@Getter
public enum ServerTaskResponseCode implements ResponseCode {

    INSTANCE_NOT_FOUND("instance [ %s ] not exists", 1);

    private final String code;
    private final String message;
    private final int args;

    ServerTaskResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.SERVER_TASK.getCodePrefix());
        this.message = message;
        this.args = args;
    }

}
