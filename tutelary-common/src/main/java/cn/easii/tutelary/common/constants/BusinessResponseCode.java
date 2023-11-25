package cn.easii.tutelary.common.constants;

/**
 * @author linpl
 */
public enum BusinessResponseCode {
    USER("10"),
    ORDER("20"),
    SETTLEMENT_ORDER("21"),
    REMOTING("30"),
    COMMAND("40"),
    ENHANCE("41"),
    CLIENT_TASK("42"),
    SERVER_TASK("43"),
    SYSTEM("90");


    private String codePrefix;

    BusinessResponseCode(final String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public String getCodePrefix() {
        return codePrefix;
    }
}
