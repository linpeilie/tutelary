package com.tutelary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(info());
    }

    private Info info() {
        return new Info()
            .title("Tutelary Server")
            .description("Tutelary Server API")
            .version("v1.0.0");
    }

}
