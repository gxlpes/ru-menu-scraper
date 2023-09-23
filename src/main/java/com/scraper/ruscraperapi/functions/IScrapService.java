package com.scraper.ruscraperapi.functions;

import java.util.Optional;

public interface IScrapService {
    Optional<Object> scrape(String ruCode);
}
