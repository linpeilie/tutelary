package cn.easii.tutelary.bean.entity;

import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import cn.easii.tutelary.installer.constants.DataTypes;
import cn.easii.tutelary.message.command.result.EnhanceAffect;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import cn.easii.tutelary.bean.domain.CommandTask;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("command_task")
@Table(comment = "命令任务", indexs = {
    @Index(unique = true, columns = {"task_id"}),
    @Index(columns = {"instance_id", "create_time"})
})
@AutoMapper(target = CommandTask.class)
public class CommandTaskEntity extends BaseEntity {

    @Column(isNull = false, comment = "命令编码", sequence = 2)
    private Integer commandCode;

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 3)
    private String instanceId;

    @Column(isNull = false, comment = "任务ID", length = 32, sequence = 4)
    private String taskId;

    @Column(isNull = false, comment = "任务参数", dataType = DataTypes.TEXT, sequence = 5)
    private String param;

    @Column(comment = "增强结果", sequence = 6)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private EnhanceAffect enhanceAffect;

    @Column(comment = "完成时间", sequence = 7)
    private LocalDateTime completeTime;

}
