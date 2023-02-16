package com.tutelary.installer;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.ImmutableMap;
import com.tutelary.common.log.Log;
import com.tutelary.common.log.LogFactory;
import com.tutelary.installer.annotation.Column;
import com.tutelary.installer.annotation.Index;
import com.tutelary.installer.annotation.Table;
import com.tutelary.installer.bean.ColumnMetadata;
import com.tutelary.installer.bean.SQL;
import com.tutelary.installer.bean.TableIndex;
import com.tutelary.installer.bean.TableMetadata;
import com.tutelary.installer.constants.DataTypes;
import com.tutelary.installer.exception.TableInstallerException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTableInstaller implements TableInstaller {

    private static final Log LOG = LogFactory.get();

    private static final String ENTITY_PACKAGE = "com.tutelary.bean.entity";

    private static final String INDEX_PREFIX = "idx_";

    private static final String UNIQUE_INDEX_PREFIX = "uni_idx_";

    private static final ImmutableMap<Class<?>, Boolean> DATA_LENGTH_MAP =
        ImmutableMap.<Class<?>, Boolean>builder().put(String.class, Boolean.TRUE).build();

    private static final ImmutableMap<Class<?>, String> DATA_TYPE_MAP = ImmutableMap.<Class<?>, String>builder()
        .put(Integer.class, DataTypes.INT)
        .put(int.class, DataTypes.INT)
        .put(Long.class, DataTypes.BIGINT)
        .put(long.class, DataTypes.BIGINT)
        .put(Double.class, DataTypes.DOUBLE)
        .put(double.class, DataTypes.DOUBLE)
        .put(String.class, DataTypes.VARCHAR)
        .put(LocalDateTime.class, DataTypes.DATETIME)
        .put(Date.class, DataTypes.DATETIME)
        .put(LocalDate.class, DataTypes.DATE)
        .build();

    @Override
    public void initializeTable(final Connection connection) {
        // 获取所有的 Entity 对象
        final Set<Class<?>> entitieseClass = ClassScanner.scanPackageByAnnotation(ENTITY_PACKAGE, Table.class);

        for (Class<?> entityClass : entitieseClass) {
            final TableMetadata tableMetadata = buildTable(entityClass);
            if (tableMetadata == null || StrUtil.isBlank(tableMetadata.getTableName())) {
                LOG.warn("{} 配置异常，没有获取到表名等信息", entityClass.getSimpleName());
                continue;
            }
            if (!tableExists(tableMetadata.getTableName(), connection)) {
                createTable(tableMetadata, connection);
                createIndex(tableMetadata, connection);
            } else {
                LOG.debug("table [ {} ] existed", tableMetadata.getTableName());
            }
        }
    }

    protected boolean tableExists(String tableName, Connection connection) {
        try {
            final ResultSet resultSet =
                connection.getMetaData().getTables(connection.getCatalog(), null, tableName, null);
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            throw new TableInstallerException(e.getMessage(), e);
        }
        return false;
    }

    private TableMetadata buildTable(Class<?> entityClass) {
        final String tableName = getTableName(entityClass);
        if (tableName == null) {
            return null;
        }
        TableMetadata tableMetadata = new TableMetadata();
        tableMetadata.setTableName(tableName);

        final Table tableAnnotation = entityClass.getAnnotation(Table.class);
        tableMetadata.setComment(tableAnnotation.comment());
        tableMetadata.setColumns(buildColumns(entityClass));
        tableMetadata.setIndexs(buildIndexes(tableName, tableAnnotation.indexs()));
        return tableMetadata;
    }

    private void createTable(TableMetadata table, Connection connection) {
        SQL sql = new SQL();
        sql.appendLine("create table if not exists `" + table.getTableName() + "` (");
        final List<ColumnMetadata> columns = table.getColumns();
        columns.sort(Comparator.comparing(ColumnMetadata::getSequence));
        for (int i = 0, len = columns.size(); i < len; i++) {
            final ColumnMetadata column = columns.get(i);
            sql.appendTab(column.getColumnName())
                .appendTab(column.getDataType())
                .append(column.isAutoIncrement(), " auto_increment ")
                .append(column.isKey(), " primary key")
                .append(!column.isKey() && StrUtil.isNotBlank(column.getDefaultValue()),
                    "default " + column.getDefaultValue())
                .append(!column.isKey() && column.isNull(), " null ")
                .append(!column.isKey() && !column.isNull(), " not null ")
                .append(StrUtil.isNotBlank(column.getComment()), "comment '" + column.getComment() + "'")
                .appendLine(i < len - 1, ",");
        }
        sql.append(")");
        sql.append(supportTableComment() && StrUtil.isNotBlank(table.getComment()),
            " comment '" + table.getComment() + "'");
        sql.appendLine(";");
        execute(connection, sql.toString());
    }

    protected void execute(Connection connection, String sql) {
        LOG.debug("execute sql : \r\n{}", sql);
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new TableInstallerException(e.getMessage(), e);
        }
    }

    protected boolean supportTableComment() {
        return true;
    }

    private void createIndex(TableMetadata table, Connection connection) {
        SQL sql = new SQL();
        for (TableIndex index : table.getIndexs()) {
            sql.append("create ")
                .append(index.isUnique(), "unique ")
                .append("index ")
                .append(index.getIndexName())
                .append(" on `")
                .append(table.getTableName())
                .append("`(")
                .append(String.join(",", index.getColumnNames()))
                .appendLine(");");
        }
        execute(connection, sql.toString());
    }

    protected String getTableName(Class<?> entityClass) {
        final Table table = entityClass.getAnnotation(Table.class);
        final String tableName = StrUtil.firstNonBlank(table.name(), table.value());
        if (tableName != null) {
            return tableName;
        }
        final TableName tableNameAnnotation = entityClass.getAnnotation(TableName.class);
        if (tableNameAnnotation != null && StrUtil.isNotBlank(tableNameAnnotation.value())) {
            return tableNameAnnotation.value();
        }
        return null;
    }

    protected List<ColumnMetadata> buildColumns(Class<?> entityClass) {
        final Field[] fields = ReflectUtil.getFields(entityClass);
        return Arrays.stream(fields).map(this::buildColumn).filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected ColumnMetadata buildColumn(Field field) {
        final String columnName = getColumnName(field);
        if (columnName == null) {
            return null;
        }
        final Column columnAnnotation = field.getAnnotation(Column.class);
        final ColumnMetadata columnMetadata = new ColumnMetadata();
        columnMetadata.setColumnName(columnName);
        columnMetadata.setSequence(columnAnnotation.sequence());
        columnMetadata.setDefaultValue(columnAnnotation.defaultValue());
        columnMetadata.setNull(columnAnnotation.isNull());
        columnMetadata.setDataType(
            StrUtil.isBlank(columnAnnotation.dataType()) ? transType(field) : columnAnnotation.dataType());
        columnMetadata.setAutoIncrement(columnAnnotation.isAutoIncrement());
        columnMetadata.setKey(columnAnnotation.isKey());
        columnMetadata.setComment(columnAnnotation.comment());
        return columnMetadata;
    }

    protected String getColumnName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        final String columnName = StrUtil.firstNonBlank(column.name(), column.value());
        if (columnName != null) {
            return columnName;
        }
        final TableField tableField = field.getAnnotation(TableField.class);
        if (tableField != null && StrUtil.isNotBlank(tableField.value())) {
            return tableField.value();
        }
        return StrUtil.toUnderlineCase(field.getName());
    }

    protected String transType(Field field) {
        final String dataType = DATA_TYPE_MAP.getOrDefault(field.getType(), DataTypes.JSON);
        final Boolean needLength = DATA_LENGTH_MAP.getOrDefault(field.getType(), Boolean.FALSE);
        return dataType + (needLength ? "(" + field.getAnnotation(Column.class).length() + ")" : "");
    }

    protected List<TableIndex> buildIndexes(String tableName, Index[] indexAnnotationList) {
        if (ArrayUtil.isEmpty(indexAnnotationList)) {
            return Collections.emptyList();
        }
        return Arrays.stream(indexAnnotationList)
            .map(index -> buildIndex(tableName, index))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    protected TableIndex buildIndex(String tableName, Index index) {
        TableIndex tableIndex = new TableIndex();
        tableIndex.setIndexName(tableName + buildIndexName(index));
        tableIndex.setUnique(index.unique());
        tableIndex.setColumnNames(index.columns());
        return tableIndex;
    }

    protected String buildIndexName(Index index) {
        if (StrUtil.isNotBlank(index.indexName())) {
            return index.indexName();
        }
        return (index.unique() ? UNIQUE_INDEX_PREFIX : INDEX_PREFIX) + String.join("_", index.columns());
    }

}
