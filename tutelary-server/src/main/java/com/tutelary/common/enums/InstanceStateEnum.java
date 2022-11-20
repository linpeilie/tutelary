package com.tutelary.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstanceStateEnum implements IEnum<Integer> {
    /**
     * 有效的
     */
    VALID(1),

    /**
     * 无效的
     */
    INVALID(0);

    private final Integer value;

}
