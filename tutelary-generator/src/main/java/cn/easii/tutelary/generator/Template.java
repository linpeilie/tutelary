package cn.easii.tutelary.generator;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private List<String> modules;
    private String classNameKey;
    private String classNameSuffix;
    private String packageNameKey;
    private String packageNameSuffix;
    private String pathKey;
    private String pathSuffix;
    private String templateName;

}
