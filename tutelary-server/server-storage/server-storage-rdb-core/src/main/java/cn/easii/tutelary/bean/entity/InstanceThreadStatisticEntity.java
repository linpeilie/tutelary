package cn.easii.tutelary.bean.entity;

import cn.easii.deps.annotation.AutoPersistence;
import cn.easii.tutelary.common.entity.BaseEntity;
import cn.easii.tutelary.installer.annotation.Column;
import cn.easii.tutelary.installer.annotation.Index;
import cn.easii.tutelary.installer.annotation.Table;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.easii.tutelary.bean.domain.InstanceThreadStatistic;
import cn.easii.tutelary.bean.domain.query.StatisticQuery;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("instance_thread_statistic")
@AutoMapper(target = InstanceThreadStatistic.class)
@Table(comment = "应用实例线程统计", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
@AutoPersistence(queryDomain = StatisticQuery.class, domain = InstanceThreadStatistic.class)
public class InstanceThreadStatisticEntity extends BaseEntity {

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 2)
    private String instanceId;

    @Column(isNull = false, comment = "当前线程数量", sequence = 3)
    private Integer threadCount;

    @Column(isNull = false, comment = "峰值线程数量", sequence = 4)
    private Integer peakThreadCount;

    @Column(isNull = false, comment = "非守护线程数量", sequence = 5)
    private Integer daemonThreadCount;

    @Column(isNull = false, comment = "虚拟机启动以来创建和启动的线程总数", sequence = 6)
    private Integer totalStartedThreadCount;

    @Column(isNull = false, comment = "上报时间", sequence = 7)
    private LocalDateTime reportTime;

}
