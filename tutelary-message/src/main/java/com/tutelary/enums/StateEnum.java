package com.tutelary.enums;

public enum StateEnum {
    SUCCESS(1),
    FAILURE(0);

    private final int value;

    StateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
