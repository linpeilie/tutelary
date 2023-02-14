package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import java.time.LocalDateTime;
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
@EqualsAndHashCode(callSuper = false)
@TableName(value = "t_instance_garbage_collectors", autoResultMap = true)
public class InstanceGarbageCollectorsEntity extends BaseEntity {

    private String instanceId;

    private String name;

    private Integer collectionCount;

    private Integer collectionTime;

    private String memoryPoolNames;

    private LocalDateTime reportTime;

}
