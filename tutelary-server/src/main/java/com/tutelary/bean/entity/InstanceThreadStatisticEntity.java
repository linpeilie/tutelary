package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 实例线程统计信息表
 * </p>
 *
 * @author linpeilie
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_instance_thread_statistic")
public class InstanceThreadStatisticEntity extends BaseEntity {

    private String instanceId;

    private Integer threadCount;

    private Integer peakThreadCount;

    private Integer daemonThreadCount;

    private Integer totalStartedThreadCount;

    private LocalDateTime reportTime;

}
