package com.tutelary.bean.api.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutelary.common.bean.api.resp.AbstractResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InstanceHostResponse extends AbstractResponse {

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

    private Long reportTimestamps;

}
