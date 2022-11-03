package com.tutelary.client.util;

import java.lang.management.ManagementFactory;

public class RuntimeUtil {

    public static int getPid() {
        return Integer.parseInt(
                ManagementFactory.getRuntimeMXBean().getSystemProperties().get("PID")
        );
    }

}
