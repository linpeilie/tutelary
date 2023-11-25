package cn.easii.tutelary.common.constants;

import lombok.Getter;

@Getter
public enum SystemResponseCode implements ResponseCode {

    ROLE_EXISTED("role [ %s ] existed", 1),
    USER_NOT_EXISTED("user [ %s ] not existed", 1);

    private final String code;
    private final String message;
    private final int args;

    SystemResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.SYSTEM.getCodePrefix());
        this.message = message;
        this.args = args;
    }

}
