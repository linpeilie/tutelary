package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.domain.InstanceGarbageCollectors;
import com.tutelary.common.converter.CommonConverter;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import io.github.zhaord.mapstruct.plus.annotations.AutoMapField;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author linpeilie
 * @since 2022-11-20
 */
@Data
@AutoMap(targetType = InstanceGarbageCollectors.class, uses = CommonConverter.class)
@EqualsAndHashCode(callSuper = false)
@TableName(value = "instance_garbage_collectors", autoResultMap = true)
@Table(comment = "应用实例垃圾收集信息", indexs = {
    @Index(columns = {"instance_id", "report_time"})
})
public class InstanceGarbageCollectorsEntity extends BaseEntity {

    @Column(isNull = false, comment = "实例ID", sequence = 2, length = 32)
    private String instanceId;

    @Column(isNull = false, comment = "垃圾收集器", sequence = 3, length = 64)
    private String name;

    @Column(isNull = false, comment = "垃圾收集次数", sequence = 4)
    private Integer collectionCount;

    @Column(isNull = false, comment = "垃圾收集耗时", sequence = 5)
    private Integer collectionTime;

    @Column(isNull = false, comment = "收集内存区域", sequence = 6, length = 512)
    private String memoryPoolNames;

    @Column(isNull = false, comment = "上报时间", sequence = 7)
    private LocalDateTime reportTime;

}
