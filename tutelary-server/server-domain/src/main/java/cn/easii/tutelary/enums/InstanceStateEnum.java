package cn.easii.tutelary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstanceStateEnum {
    /**
     * 有效的
     */
    VALID(1),

    /**
     * 无效的
     */
    INVALID(0);

    private final Integer value;

    public static InstanceStateEnum getByValue(Integer value) {
        for (InstanceStateEnum instanceStateEnum : InstanceStateEnum.values()) {
            if (instanceStateEnum.getValue().equals(value)) {
                return instanceStateEnum;
            }
        }
        return null;
    }

}
