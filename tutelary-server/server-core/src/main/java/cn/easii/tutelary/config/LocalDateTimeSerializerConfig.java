package cn.easii.tutelary.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LocalDateTimeSerializerConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateFormat;

    @Value("${spring.jackson.local-date-time-format:yyyy-MM-dd HH:mm:ss}")
    private String localDateTimeFormat;

    @Value("${spring.jackson.local-date-format:yyyy-MM-dd}")
    private String localDateFormat;

    @Value("${spring.jackson.local-time-format:HH:mm:ss}")
    private String localTimeFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
            .serializerByType(
                LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(localDateTimeFormat))
            )
            .serializerByType(
                LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(localDateFormat))
            )
            .serializerByType(
                LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(localTimeFormat))
            )
            .serializerByType(Date.class, new DateSerializer(false, new SimpleDateFormat(dateFormat)))
            .deserializerByType(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(localDateTimeFormat))
            )
            .deserializerByType(
                LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(localDateFormat))
            )
            .deserializerByType(
                LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(localTimeFormat))
            )
            .deserializerByType(
                Date.class,
                new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance,
                    new SimpleDateFormat(dateFormat), dateFormat
                )
            )
            .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
            ;
    }

}
