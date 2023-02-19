package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tutelary.bean.domain.InstanceThreadDetailCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.common.annotation.AutoPersistence;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import com.tutelary.message.command.result.ThreadDetail;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("instance_thread_detail")
@AutoMap(targetType = InstanceThreadDetailCommand.class)
@Table(comment = "实例线程详情信息", indexs = {
    @Index(columns = "task_id", unique = true),
    @Index(columns = {"instance_id", "create_time"})
})
@AutoPersistence(queryDomain = CommandTaskQuery.class, domain = InstanceThreadDetailCommand.class)
public class InstanceThreadDetailCommandEntity extends BaseEntity {

    @Column(isNull = false, comment = "任务ID", length = 32, sequence = 2)
    private String taskId;

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 3)
    private String instanceId;

    @Column(isNull = false, comment = "结果", sequence = 4)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThreadDetail result;

}
