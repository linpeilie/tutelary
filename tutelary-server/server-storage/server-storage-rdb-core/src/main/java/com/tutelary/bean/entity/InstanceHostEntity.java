package com.tutelary.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.tutelary.bean.domain.InstanceHost;
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
 * 实例主机信息表
 * </p>
 *
 * @author linpeilie
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("instance_host")
@AutoMap(targetType = InstanceHost.class)
@Table(comment = "实例物理机信息", indexs = {
    @Index(columns = "instance_id", unique = true)
})
public class InstanceHostEntity extends BaseEntity {

    @Column(isNull = false, comment = "实例ID", length = 32, sequence = 2)
    private String instanceId;

    @Column(isNull = false, comment = "实例host名称", length = 128, sequence = 3)
    private String hostName;

    @Column(isNull = false, comment = "IP地址", length = 15, sequence = 4)
    private String hostAddress;

    @Column(isNull = false, comment = "系统名称", length = 64, sequence = 5)
    private String osName;

    @Column(isNull = false, comment = "系统架构", length = 16, sequence = 6)
    private String arch;

    @Column(isNull = false, comment = "CPU可用核心数", sequence = 7)
    private Integer availableProcessors;

    @Column(isNull = false, comment = "可用于正在运行的进程的虚拟机内存量「单位/kb」", sequence = 8)
    private Long committedVirtualMemory;

    @Column(isNull = false, comment = "总物理内存量「单位/kb」", sequence = 9)
    private Long totalPhysicalMemorySize;

    @Column(isNull = false, comment = "空闲物理内存量「单位/kb」", sequence = 10)
    private Long freePhysicalMemorySize;

    @Column(isNull = false, comment = "总交换分区内存量「单位/kb」", sequence = 11)
    private Long totalSwapSpaceSize;

    @Column(isNull = false, comment = "空闲交换分区内存量「单位/kb」", sequence = 12)
    private Long freeSwapSpaceSize;

    @Column(isNull = false, comment = "空闲硬盘空间「单位/kb」", sequence = 13)
    private Long diskFreeSpace;

    @Column(isNull = false, comment = "可用硬盘空间「单位/kb」", sequence = 14)
    private Long diskUsableSpace;

    @Column(isNull = false, comment = "总硬盘空间「单位/kb」", sequence = 15)
    private Long diskTotalSpace;

    @Column(isNull = false, comment = "上报时间", sequence = 16)
    private LocalDateTime reportTime;

}
