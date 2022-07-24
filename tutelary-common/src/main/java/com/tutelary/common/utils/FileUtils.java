package com.tutelary.common.utils;

import cn.hutool.core.io.FileUtil;

import java.io.File;

public class FileUtils {

    public static File mkdir(String parentPath, String childPath, boolean clean) {
        File tutelaryDir = new File(parentPath, childPath);
        if (tutelaryDir.exists()) {
            if (clean) {
                FileUtil.clean(tutelaryDir);
            }
            return tutelaryDir;
        }
        if (tutelaryDir.mkdir()) {
            return tutelaryDir;
        }
        return null;
    }

}
