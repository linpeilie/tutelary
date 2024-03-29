package cn.easii.tutelary.message.command.domain;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import java.io.Serializable;
import lombok.Data;

@Data
@ProtobufClass
public class HostInfo implements Serializable {

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 主机地址
     */
    private String hostAddress;

    /**
     * 系统名称
     */
    private String osName;

    /**
     * 系统架构
     */
    private String arch;

    /**
     * CPU 核心数
     */
    private int availableProcessors;

    /**
     * 可用于正在运行的进程的虚拟内存量，单位：kb
     */
    private long committedVirtualMemory;

    /**
     * 总物理内存量，单位：kb
     */
    private long totalPhysicalMemorySize;

    /**
     * 空闲物理内存量，单位：kb
     */
    private long freePhysicalMemorySize;

    /**
     * 总交换分区内存量，单位：kb
     */
    private long totalSwapSpaceSize;

    /**
     * 空闲交换分区内存量，单位：kb
     */
    private long freeSwapSpaceSize;

    /**
     * 空闲硬盘空间，单位：kb
     */
    private long diskFreeSpace;

    /**
     * 可用硬盘空间，单位：kb
     */
    private long diskUsableSpace;

    /**
     * 总硬盘空间，单位：kb
     */
    private long diskTotalSpace;

}
