package com.techbypranay.socialmediaapp.common;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalArrayConfig {

    @Bean
    public GlobalArray globalArray() {
        return new GlobalArray();
    }
}