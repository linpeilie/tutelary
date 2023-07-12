package com.tutelary.client.core.file;

import cn.hutool.core.io.FileUtil;
import com.tutelary.client.ClientBootstrap;
import java.io.File;

public class FileManager {

    public static String workspaceFolder() {
        return FileUtil.getUserHomePath() + File.separator + ".tutelary" + File.separator + ClientBootstrap.instanceId;
    }

    public static String dumpFolder() {
        final String dumpFolder = workspaceFolder() + File.separator + "dump";
        if (!FileUtil.exist(dumpFolder)) {
            FileUtil.mkParentDirs(dumpFolder);
            FileUtil.mkdir(dumpFolder);
        }
        return dumpFolder;
    }

}
