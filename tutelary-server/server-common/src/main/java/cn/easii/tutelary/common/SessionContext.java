package cn.easii.tutelary.common;

public class SessionContext {

    private static final ThreadLocal<String> CURRENT_USER_ID_LOCAL = new ThreadLocal<>();

    public static void clear() {
        CURRENT_USER_ID_LOCAL.remove();
    }

    public static String getUserId() {
        return CURRENT_USER_ID_LOCAL.get();
    }

    public static void setUserId(String userId) {
        CURRENT_USER_ID_LOCAL.set(userId);
    }

}
