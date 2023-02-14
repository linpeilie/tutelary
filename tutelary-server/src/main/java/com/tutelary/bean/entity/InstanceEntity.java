package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.entity.type.StringListTypeHandler;
import com.tutelary.bean.entity.type.StringMapTypeHandler;
import com.tutelary.common.bean.entity.BaseEntity;
import com.tutelary.common.enums.InstanceStateEnum;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@TableName(value = "t_instance", autoResultMap = true)
public class InstanceEntity extends BaseEntity {

    private String instanceId;

    private String appName;

    private String ip;

    private LocalDateTime registerDate;

    private InstanceStateEnum state;

    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> inputArguments;

    @TableField(typeHandler = StringMapTypeHandler.class)
    private Map<String, String> systemProperties;

    private String classPath;

    private String libraryPath;

    private String vmVendor;

    private String vmName;

    private String vmVersion;

    private String jdkVersion;

    private LocalDateTime startTime;

}
