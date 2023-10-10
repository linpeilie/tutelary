package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.tutelary.bean.domain.GetStaticCommand;
import com.tutelary.bean.domain.query.CommandTaskQuery;
import com.tutelary.common.annotation.AutoPersistence;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import com.tutelary.message.command.result.GetStaticResponse;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;

@AutoMapper(target = GetStaticCommand.class)
@Table(comment = "获取静态属性结果", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
@AutoPersistence(queryDomain = CommandTaskQuery.class, domain = GetStaticCommand.class)
public class GetStaticEntity extends BaseEntity {

    @Column(isNull = false, comment = "任务ID", length = 32, sequence = 2)
    private String taskId;

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 3)
    private String instanceId;

    @Column(isNull = false, comment = "上报时间", sequence = 4)
    private LocalDateTime reportTime;

    @Column(isNull = false, comment = "结果", sequence = 5)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private GetStaticResponse result;

}
