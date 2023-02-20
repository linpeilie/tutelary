package com.tutelary.bean.resp;

import com.tutelary.bean.domain.Instance;
import io.github.zhaord.mapstruct.plus.annotations.AutoMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMap(targetType = Instance.class)
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
