package cn.easii.tutelary.installer.bean;

import lombok.Data;

/**
 * 表列
 */
@Data
public class ColumnMetadata {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 顺序
     */
    private Integer sequence;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否可以为null
     */
    private boolean isNull;

    /**
     * 字段类型，类型[(lengthh)]
     */
    private String dataType;

    /**
     * 是否主键
     */
    private boolean isKey;

    /**
     * 是否自动增长
     */
    private boolean autoIncrement;

    /**
     * 列注释
     */
    private String comment;

}
