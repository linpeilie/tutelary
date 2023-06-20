package com.tutelary.common.constants;

/**
 * @author linpl
 */
public enum BusinessResponseCode {
    USER("10"),
    ORDER("20"),
    SETTLEMENT_ORDER("21"),
    SYSTEM("90");


    private String codePrefix;

    BusinessResponseCode(final String codePrefix) {
        this.codePrefix = codePrefix;
    }

    public String getCodePrefix() {
        return codePrefix;
    }
}
