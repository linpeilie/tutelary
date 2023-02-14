package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_command_task")
public class CommandTaskEntity extends BaseEntity {

    private Integer commandCode;

    private String instanceId;

    private String taskId;

    private String param;

    private LocalDateTime completeTime;

}
