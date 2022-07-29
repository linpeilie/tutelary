package com.tutelary.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.tutelary.mapper")
public class MybatisPlusConfig {
}
