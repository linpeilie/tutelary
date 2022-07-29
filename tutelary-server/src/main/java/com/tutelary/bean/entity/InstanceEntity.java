package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "t_instance")
public class InstanceEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

}
