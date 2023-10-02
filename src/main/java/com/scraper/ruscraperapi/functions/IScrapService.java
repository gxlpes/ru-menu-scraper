package com.scraper.ruscraperapi.functions;

import org.springframework.http.ResponseEntity;


public interface IScrapService {
    ResponseEntity scrape(String ruCode);
}
