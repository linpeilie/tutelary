package cn.easii.tutelary.common.entity;

import cn.easii.tutelary.installer.annotation.Column;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    @Column(isKey = true, isAutoIncrement = true, sequence = 1)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @Column(isNull = false, sequence = Integer.MAX_VALUE - 3, comment = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    @Column(sequence = Integer.MAX_VALUE - 2, comment = "创建用户")
    private String createUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(isNull = false, sequence = Integer.MAX_VALUE - 1, comment = "修改时间")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(sequence = Integer.MAX_VALUE, comment = "修改用户ID")
    private String updateUserId;

}
