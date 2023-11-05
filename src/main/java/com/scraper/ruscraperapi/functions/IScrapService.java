package com.scraper.ruscraperapi.functions;

import com.scraper.ruscraperapi.data.response.ResponseMenu;


public interface IScrapService {
    ResponseMenu scrape(String ruCode);
}
