package com.dineease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
@EnableAsync
public class DineeaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DineeaseApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); 
        return mapper;
    }
}