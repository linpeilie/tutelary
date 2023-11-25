package cn.easii.tutelary.installer;

import java.sql.Connection;
import javax.sql.DataSource;

public class DefaultTableInstallerInvoker implements TableInstallerInvoker {

    private DataSource dataSource;

    private TableInstaller tableInstaller;

    public DefaultTableInstallerInvoker(DataSource dataSource, TableInstaller tableInstaller) {
        this.dataSource = dataSource;
        this.tableInstaller = tableInstaller;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            tableInstaller.initializeTable(connection);
        }
    }

}
