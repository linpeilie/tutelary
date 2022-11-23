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

    private List<Integer> availableProcessors;

    private List<Long> committedVirtualMemory;

    private List<Long> totalPhysicalMemorySize;

    private List<Long> freePhysicalMemorySize;

    private List<Long> totalSwapSpaceSize;

    private List<Long> freeSwapSpaceSize;

    private Long diskFreeSpace;

    private Long diskUsableSpace;

    private Long diskTotalSpace;

    private List<Long> reportTimestamps;

}
