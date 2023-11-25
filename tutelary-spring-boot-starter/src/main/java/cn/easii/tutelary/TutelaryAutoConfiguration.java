package cn.easii.tutelary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(name = "spring.tutelary.enabled", matchIfMissing = true)
@EnableConfigurationProperties({TutelaryProperties.class})
public class TutelaryAutoConfiguration {

    private final ApplicationContext applicationContext;

    private final TutelaryProperties tutelaryProperties;

    @Autowired
    public TutelaryAutoConfiguration(ApplicationContext applicationContext, TutelaryProperties tutelaryProperties) {
        this.applicationContext = applicationContext;
        this.tutelaryProperties = tutelaryProperties;
    }

    @Bean
    public Tutelary tutelaryAgent() {
        return new Tutelary(applicationContext, tutelaryProperties);
    }

}
