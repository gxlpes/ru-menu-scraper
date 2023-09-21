package com.scraper.ruscraperapi;

import com.scraper.ruscraperapi.service.ScrapService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.function.Function;

@SpringBootApplication
public class RuScraperApiApplication {
	private final ScrapService scrapService;

	public RuScraperApiApplication(ScrapService scrapService) {
		this.scrapService = scrapService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RuScraperApiApplication.class, args);
	}

	@Bean
	public Function<String, Object> scraperMenu() {
		return input -> {
			try {
			return scrapService.apply(input);
			} catch (Exception e) {
				e.printStackTrace();
				return "Error: " + e.getMessage();
			}
		};
	}}