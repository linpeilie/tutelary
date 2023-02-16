package com.tutelary.installer;

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
        tableInstaller.initializeTable(dataSource.getConnection());
    }

}
