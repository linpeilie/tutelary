package com.tutelary.bean.entity;

import cn.easii.deps.annotation.AutoPersistence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tutelary.bean.domain.HeapDumpCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import com.tutelary.message.command.result.HeapDumpResponse;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("instance_heap_dump")
@AutoMapper(target = HeapDumpCommand.class)
@Table(comment = "实例HeapDump结果", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
@AutoPersistence(queryDomain = CommandTaskQuery.class, domain = HeapDumpCommand.class)
public class HeapDumpCommandEntity extends BaseEntity {

    @Column(isNull = false, comment = "任务ID", length = 32, sequence = 2)
    private String taskId;

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 3)
    private String instanceId;

    @Column(isNull = false, comment = "上报时间", sequence = 4)
    private LocalDateTime reportTime;

    @Column(isNull = false, comment = "结果", sequence = 5)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private HeapDumpResponse result;

}
