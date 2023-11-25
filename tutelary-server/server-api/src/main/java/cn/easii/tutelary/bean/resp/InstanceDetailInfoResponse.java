package cn.easii.tutelary.bean.resp;

import cn.easii.tutelary.bean.domain.Instance;
import io.github.linpeilie.annotations.AutoMapper;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Instance.class)
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
