package cn.easii.tutelary.config;

import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    @Value("${api-package:cn.easii.tutelary.api}")
    private String apiPackagePath;

    @Value("${api-prefix:/api}")
    private String apiPrefix;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);

        if (Objects.nonNull(mappingInfo)) {
            String prefix = this.getPrefix(handlerType);
            if (prefix != null) {
                String[] paths = mappingInfo.getPatternValues()
                    .stream()
                    .map(path -> prefix + path)
                    .toArray(String[]::new);
                return mappingInfo.mutate()
                    .paths(paths)
                    .build();
            }
        }

        return mappingInfo;
    }

    private String getPrefix(Class<?> handleType) {
        String packageName = handleType.getPackage().getName();
        if (packageName.startsWith(this.apiPackagePath)) {
            return apiPrefix;
        } else {
            return null;
        }
    }

}
