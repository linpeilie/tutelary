package cn.easii.tutelary.client.constants;

import cn.easii.tutelary.common.constants.BusinessResponseCode;
import cn.easii.tutelary.common.constants.ResponseCode;

public enum EnhanceResponseCode implements ResponseCode {

    ENHANCE_CLASS_IS_NULL("class not found",0),
    ENHANCE_CLASS_NOT_FOUND_BY_CLASSLOADER("Only classes loaded by the %s can be enhanced",1),
    LAMBDA_CANNOT_BE_ENHANCED("lambda class cannot be enhanced", 0),
    INTERFACE_CANNOT_BE_ENHANCED("interface cannot be enhanced", 0)
    ;

    EnhanceResponseCode(String message, int args) {
        this.code = formatCode(BusinessResponseCode.ENHANCE.getCodePrefix());
        this.message = message;
        this.args = args;
    }

    private final String code;
    private final String message;
    private final int args;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getArgs() {
        return args;
    }
}
