package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.domain.Instance;
import com.tutelary.bean.entity.type.StringListTypeHandler;
import com.tutelary.bean.entity.type.StringMapTypeHandler;
import com.tutelary.common.converter.CommonConverter;
import com.tutelary.common.entity.BaseEntity;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import io.github.linpeilie.annotations.AutoMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@AutoMapper(target = Instance.class, uses = CommonConverter.class)
@TableName(value = "instance", autoResultMap = true)
@Table(comment = "实例", indexs = {
    @Index(unique = true, columns = "instance_id")
})
public class InstanceEntity extends BaseEntity {

    @Column(isNull = false, length = 32, comment = "实例ID", sequence = 2)
    private String instanceId;

    @Column(isNull = false, length = 64, comment = "应用名称", sequence = 3)
    private String appName;

    @Column(isNull = false, length = 15, comment = "IP", sequence = 4)
    private String ip;

    @Column(isNull = false, comment = "注册时间", sequence = 5)
    private LocalDateTime registerDate;

    @Column(isNull = false, comment = "启动输入参数", sequence = 6)
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> inputArguments;

    @Column(isNull = false, comment = "系统参数", sequence = 7)
    @TableField(typeHandler = StringMapTypeHandler.class)
    private Map<String, String> systemProperties;

    @Column(isNull = false, sequence = 8, dataType = "text")
    private String classPath;

    @Column(isNull = false, comment = "依赖路径", sequence = 9, dataType = "text")
    private String libraryPath;

    @Column(isNull = false, comment = "Java 虚拟机实现供应商", length = 64, sequence = 10)
    private String vmVendor;

    @Column(isNull = false, comment = "Java 虚拟机实现版本", length = 64, sequence = 11)
    private String vmName;

    @Column(isNull = false, comment = "Java 虚拟机实现版本", length = 32, sequence = 12)
    private String vmVersion;

    @Column(isNull = false, comment = "JDK 版本", length = 32, sequence = 13)
    private String jdkVersion;

    @Column(isNull = false, comment = "启动时间", sequence = 14)
    private LocalDateTime startTime;

}
