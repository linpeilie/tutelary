package com.tutelary.common.constants;

public enum CommonResponseCode implements ResponseCode {

    SUCCESS("9999000", "操作成功", 0),

    SERVER_ERROR("99990001", "服务器错误", 0),

    ILLEGAL_ARGUMENT("99990002", "参数错误 : %s", 1),
    NO_PERMISSION("99990003", "无权限", 0),
    NO_LOGIN("99990004", "用户未登录", 0),
    AUTHENTICATION_FAILURE("99990005", "用户名或密码错误", 0),
    USER_STATE_ERROR("99990006", "用户状态异常", 0),

    NOT_FOUND("99990011", "数据不存在", 0),
    ALREADY_EXISTS("99990012", "数据已存在", 0),

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
