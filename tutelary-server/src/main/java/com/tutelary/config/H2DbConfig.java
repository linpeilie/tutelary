package com.tutelary.config;

import com.tutelary.common.constants.PersistentMannerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource ("classpath:h2.properties")
@ConditionalOnProperty (name = "tutelary.server.persistent", havingValue = PersistentMannerConstants.MEMORY)
@Slf4j
public class H2DbConfig {

    @Value ("${jdbc.url}")
    private String url;

    @Value ("${jdbc.driverClassName}")
    private String driverClassName;

    @Value ("${jdbc.username}")
    private String username;

    @Value ("${jdbc.password}")
    private String password;

    @Value("${schema.path}")
    private List<String> schema;

    @Value("${data.path}")
    private List<String> data;

    @Bean
    public H2ConsoleProperties h2ConsoleProperties() {
        log.info("h2 console url : {}", "http://localhost:8082/h2-console");
        H2ConsoleProperties h2ConsoleProperties = new H2ConsoleProperties();
        h2ConsoleProperties.setEnabled(true);
        return h2ConsoleProperties;
    }

    @Bean
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setDriverClassName(driverClassName);
        dataSourceProperties.setUrl(url);
        dataSourceProperties.setUsername(username);
        dataSourceProperties.setPassword(password);
        dataSourceProperties.setSchema(schema);
        dataSourceProperties.setData(data);
        return dataSourceProperties;
    }

}
