package com.tutelary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@ConditionalOnProperty(name = "spring.tutelary.enabled", matchIfMissing = true)
@EnableConfigurationProperties({TutelaryProperties.class})
public class TutelaryAutoConfiguration {

    private final ConfigurableEnvironment environment;

    private final TutelaryProperties tutelaryProperties;

    @Autowired
    public TutelaryAutoConfiguration(ConfigurableEnvironment environment, TutelaryProperties tutelaryProperties) {
        this.environment = environment;
        this.tutelaryProperties = tutelaryProperties;
    }

    @Bean
    public Tutelary tutelaryAgent() {
        return new Tutelary(environment, tutelaryProperties);
    }

}
