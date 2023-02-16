package com.tutelary.bean.api.resp;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstanceDetailInfoResponse extends InstanceInfoResponse {

    private List<String> inputArguments;

    private Map<String, String> systemProperties;

    private String classPath;

    private String libraryPath;

    private String vmVendor;

    private String vmName;

    private String vmVersion;

    private String jdkVersion;

}
