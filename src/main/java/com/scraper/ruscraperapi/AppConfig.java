package com.scraper.ruscraperapi;

import com.scraper.ruscraperapi.data.ru.RuFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RuFactory ruFactory() {
        return new RuFactory();
    }
}
