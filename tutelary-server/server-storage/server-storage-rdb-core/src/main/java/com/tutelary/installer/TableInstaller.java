package com.tutelary.installer;

import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;

/**
 * 表结构创建接口
 */
public interface TableInstaller {

    void initializeTable(Connection connection);

}
