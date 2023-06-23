package com.tutelary.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStateEnum {

    AVAILABLE("00", "正常"),
    DISABLED("10", "停用");

    private final String state;
    private final String desc;

    public static UserStateEnum getByValue(String state) {
        for (UserStateEnum userStateEnum : UserStateEnum.values()) {
            if (userStateEnum.getState().equals(state)) {
                return userStateEnum;
            }
        }
        return null;
    }

}
