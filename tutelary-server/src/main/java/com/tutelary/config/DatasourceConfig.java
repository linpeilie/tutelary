package com.tutelary.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    // @Bean
    // public DataSource dataSource(DataSourceProperties dataSourceProperties) {
    //     return dataSourceProperties.initializeDataSourceBuilder()
    //         .type(HikariDataSource.class)
    //         .build();
    // }

}
