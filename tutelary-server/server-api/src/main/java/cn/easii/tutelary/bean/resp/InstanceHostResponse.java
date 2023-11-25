package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.common.bean.resp.AbstractResponse;
import cn.easii.tutelary.bean.domain.InstanceHost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@Data
@AutoMapper(target = InstanceHost.class)
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
