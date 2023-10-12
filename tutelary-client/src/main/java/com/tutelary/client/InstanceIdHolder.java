package com.tutelary.client;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.tutelary.client.core.file.FileManager;
import java.io.File;
import java.nio.charset.Charset;
import lombok.Getter;

public class InstanceIdHolder {

    @Getter
    private static final String instanceId;

    static {
        String instanceIdFolder = FileUtil.getUserHomePath();
        File idFile = new File(instanceIdFolder, ".tutelaryId");
        if (idFile.exists()) {
            String str = FileUtil.readString(idFile, Charset.defaultCharset());
            if (StrUtil.isNotEmpty(str)) {
                instanceId = str;
            } else {
                instanceId = generateId();
            }
        } else {
            instanceId = generateId();
        }
        FileUtil.writeString(instanceId, idFile, Charset.defaultCharset());
    }

    private static String generateId() {
        return UUID.randomUUID().toString(true);
    }

}
