package cn.easii.tutelary.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {

    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

}
