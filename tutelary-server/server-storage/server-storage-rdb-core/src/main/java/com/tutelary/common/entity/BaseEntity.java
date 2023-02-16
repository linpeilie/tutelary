package com.tutelary.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tutelary.installer.annotation.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    @Column(isKey = true, isAutoIncrement = true, sequence = 1)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @Column(isNull = false, sequence = Integer.MAX_VALUE - 1, comment = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(isNull = false, sequence = Integer.MAX_VALUE, comment = "修改时间")
    private LocalDateTime updateTime;

}
