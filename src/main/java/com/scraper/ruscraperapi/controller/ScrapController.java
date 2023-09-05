package com.scraper.ruscraperapi.controller;

import com.scraper.ruscraperapi.service.ScrapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class ScrapController {

    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }


    @GetMapping("/scrape/{ruCode}")
    public Optional<Object> getMenu(@PathVariable String ruCode) {

       return scrapService.getMenuToday(ruCode);

    }
}
