package cn.easii.tutelary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableStatusEnum {

    ENABLED(1),
    NOT_ENABLED(0);

    private final Integer status;

    public static EnableStatusEnum getByStatus(Integer status) {
        for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
            if (enableStatusEnum.getStatus().equals(status)) {
                return enableStatusEnum;
            }
        }
        return null;
    }

}
