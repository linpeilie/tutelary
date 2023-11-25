package cn.easii.tutelary.installer.bean;

import java.util.List;
import lombok.Data;

/**
 * 表结构 元数据
 */
@Data
public class TableMetadata {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 列
     */
    private List<ColumnMetadata> columns;

    /**
     * 索引
     */
    private List<TableIndex> indexs;

}
