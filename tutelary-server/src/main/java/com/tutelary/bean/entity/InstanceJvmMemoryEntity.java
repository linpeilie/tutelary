package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.common.bean.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

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
@TableName("t_instance_jvm_memory")
public class InstanceJvmMemoryEntity extends BaseEntity {

    private String instanceId;

    private String type;

    private String name;

    private Integer used;

    private Integer committed;

    private Integer max;

    private LocalDateTime reportTime;

}
