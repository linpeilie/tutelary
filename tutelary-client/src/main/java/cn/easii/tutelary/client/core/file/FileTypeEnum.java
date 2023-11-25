package cn.easii.tutelary.client.core.file;

public enum FileTypeEnum {
    HEAP_DUMP(1);

    private final int type;

    FileTypeEnum(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
