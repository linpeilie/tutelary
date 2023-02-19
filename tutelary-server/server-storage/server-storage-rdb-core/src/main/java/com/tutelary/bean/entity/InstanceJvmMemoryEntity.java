package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.domain.InstanceJvmMemory;
import com.tutelary.bean.domain.query.StatisticQuery;
import com.tutelary.common.annotation.AutoPersistence;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author linpeilie
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("instance_jvm_memory")
@AutoMap(targetType = InstanceJvmMemory.class)
@Table(comment = "实例JVM内存信息", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
@AutoPersistence(queryDomain = StatisticQuery.class, domain = InstanceJvmMemory.class)
public class InstanceJvmMemoryEntity extends BaseEntity {

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 2)
    private String instanceId;

    @Column(isNull = false, comment = "内存类型「HEAP、NON_HEAP」", length = 8, sequence = 3)
    private String type;

    @Column(isNull = false, comment = "内存区域名称", length = 64, sequence = 4)
    private String name;

    @Column(isNull = false, comment = "已使用内存「单位/kb」", sequence = 5)
    private Integer used;

    @Column(isNull = false, comment = "已提交内存「单位/kb」", sequence = 6)
    private Integer committed;

    @Column(isNull = false, comment = "最大内存「单位/kb」", sequence = 7)
    private Integer max;

    @Column(isNull = false, comment = "上报时间", sequence = 8)
    private LocalDateTime reportTime;

}
