package com.tutelary.client.constants;

public interface LoggerConstant {

    String name = "name";

    String clazz = "clazz";

    String classLoader = "classLoader";

    String classLoaderHash = "classLoaderHash";

    String level = "level";

    String effectiveLevel = "effectiveLevel";

    String additivity = "additivity";

    String codeSource = "codeSource";

    String config = "config";

    String appenders = "appenders";

    interface AppendersConstant {
        String name = "name";

        String clazz = "clazz";

        String file = "file";

        String blocking = "blocking";

        String appenderRef = "appenderRef";

        String target = "target";
    }

}
