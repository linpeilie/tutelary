package cn.easii.tutelary.command.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileDownloadStore {

    private static final Map<String, FileDownloadMetadata> METADATA_MAP = new ConcurrentHashMap<>();

    public static void add(String identifier, FileDownloadMetadata fileDownloadMetadata) {
        METADATA_MAP.put(identifier, fileDownloadMetadata);
    }

    public static void remove(String identifier) {
        METADATA_MAP.remove(identifier);
    }

    public static FileDownloadMetadata get(String identifier) {
        return METADATA_MAP.get(identifier);
    }

}
