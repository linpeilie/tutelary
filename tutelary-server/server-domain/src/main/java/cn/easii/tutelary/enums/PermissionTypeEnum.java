package cn.easii.tutelary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionTypeEnum {

    /**
     * 菜单
     */
    MENU("M"),

    /**
     * 资源
     */
    RESOURCE("R");

    private final String type;

    public static PermissionTypeEnum getByType(String type) {
        for (PermissionTypeEnum permissionTypeEnum : PermissionTypeEnum.values()) {
            if (permissionTypeEnum.getType().equals(type)) {
                return permissionTypeEnum;
            }
        }
        return null;
    }

}
