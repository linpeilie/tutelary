package com.tutelary.common.constants;

public enum CommonResponseCode implements ResponseCode {

    SUCCESS("9999000", "操作成功", 0),

    SERVER_ERROR("99990001", "服务器错误", 0),
    SERVER_BUSY("99990002", "系统繁忙", 0),
    ILLEGAL_ARGUMENT("99990003", "参数错误 : %s", 1),
    NO_PERMISSION("99990010", "无权限", 0),
    NO_LOGIN("999900011", "用户未登录", 0),
    AUTHENTICATION_FAILURE("99990012", "用户名或密码错误", 0),
    USER_STATE_DISABLED("999900013", "账号已被停用", 0),
    USER_LOGIN_FAULT_EXCEED_MAX("99990014", "密码输入错误%s次，账户锁定%s分钟", 2),

    NOT_FOUND("99990020", "数据不存在", 0),
    ALREADY_EXISTS("99990021", "数据已存在", 0),

    DB_FAILURE("99990031", "数据库异常", 0),

    LOCK_FAILURE("99990041", "获取锁失败", 0),

    UPLOAD_FAILURE("99990051", "上传文件异常", 0),
    UPLOAD_FILE_SIZE_EXCEED("99990052", "超过上传文件大小限制", 0);

    CommonResponseCode(final String code, final String message, final int args) {
        this.code = code;
        this.message = message;
        this.args = args;
    }

    private String code;
    private String message;
    private int args;

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
