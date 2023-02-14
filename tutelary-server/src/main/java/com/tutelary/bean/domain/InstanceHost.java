package com.tutelary.bean.domain;

import com.tutelary.common.bean.domain.BaseDomain;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class InstanceHost extends BaseDomain {
    private String instanceId;

    private String hostName;

    private String hostAddress;

    private String osName;

    private String arch;

    private Integer availableProcessors;

    private Long committedVirtualMemory;

    private Long totalPhysicalMemorySize;

    private Long freePhysicalMemorySize;

    private Long totalSwapSpaceSize;

    private Long freeSwapSpaceSize;

    private Long diskFreeSpace;

    private Long diskUsableSpace;

    private Long diskTotalSpace;

    private LocalDateTime reportTime;
}
