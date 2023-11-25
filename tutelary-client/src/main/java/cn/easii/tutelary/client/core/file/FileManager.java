package cn.easii.tutelary.client.core.file;

import cn.hutool.core.io.FileUtil;
import cn.easii.tutelary.client.InstanceIdHolder;
import java.io.File;

public class FileManager {

    public static String workspaceFolder() {
        return FileUtil.getUserHomePath() + File.separator + ".tutelary" + File.separator +
               InstanceIdHolder.getInstanceId();
    }

    private static void ensureFolderMkdir(String folder) {
        if (!FileUtil.exist(folder)) {
            FileUtil.mkParentDirs(folder);
            FileUtil.mkdir(folder);
        }
    }

    public static String heapDumpFolder() {
        final String heapDumpFolder = workspaceFolder() + File.separator + "heapDump";
        ensureFolderMkdir(heapDumpFolder);
        return heapDumpFolder;
    }

    public static String classDumpFolder() {
        final String classDumpFolder = workspaceFolder() + File.separator + "classDump";
        ensureFolderMkdir(classDumpFolder);
        return classDumpFolder;
    }

    public static String enhanceClassDumpFolder() {
        final String enhanceClassDumpFolder = workspaceFolder() + File.separator + "enhanceClassDump";
        ensureFolderMkdir(enhanceClassDumpFolder);
        return enhanceClassDumpFolder;
    }

    /**
     * compiles the generated class directory
     */
    public static String compilerClassFolder() {
        String compilerClassFolder = workspaceFolder() + File.separator + "compilerClass";
        ensureFolderMkdir(compilerClassFolder);
        return compilerClassFolder;
    }

}
