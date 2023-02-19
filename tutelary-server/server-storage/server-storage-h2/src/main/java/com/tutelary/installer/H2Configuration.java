package com.tutelary.installer;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class H2Configuration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "storage.h2.properties")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

}
