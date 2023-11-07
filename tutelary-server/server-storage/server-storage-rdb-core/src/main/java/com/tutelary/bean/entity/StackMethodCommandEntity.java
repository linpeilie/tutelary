package com.tutelary.bean.entity;

import cn.easii.deps.annotation.AutoPersistence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tutelary.bean.domain.StackMethodCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import com.tutelary.message.command.result.StackResponse;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("instance_stack_method")
@AutoMapper(target = StackMethodCommand.class)
@Table(comment = "方法堆栈信息", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
@AutoPersistence(queryDomain = CommandTaskQuery.class, domain = StackMethodCommand.class)
public class StackMethodCommandEntity extends BaseEntity {


    @Column(isNull = false, comment = "任务ID", length = 32, sequence = 2)
    private String taskId;

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 3)
    private String instanceId;

    @Column(isNull = false, comment = "上报时间", sequence = 4)
    private LocalDateTime reportTime;

    @Column(isNull = false, comment = "结果", sequence = 5)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private StackResponse result;

}
