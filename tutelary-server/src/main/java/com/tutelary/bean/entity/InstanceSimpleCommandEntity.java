package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_instance_simple_command")
public class InstanceSimpleCommandEntity extends BaseEntity {

    private String instanceId;

    private String taskId;

    private String param;

    private String result;

}
