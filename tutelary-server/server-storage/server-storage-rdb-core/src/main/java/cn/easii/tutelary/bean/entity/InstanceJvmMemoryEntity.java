package cn.easii.tutelary.bean.entity;

import cn.easii.deps.annotation.AutoPersistence;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.easii.tutelary.bean.domain.InstanceJvmMemory;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import io.github.linpeilie.annotations.AutoMapper;
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
@AutoMapper(target = InstanceJvmMemory.class)
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
