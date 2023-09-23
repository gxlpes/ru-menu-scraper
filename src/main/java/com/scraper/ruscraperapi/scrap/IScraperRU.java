package com.scraper.ruscraperapi.scrap;

import org.jsoup.select.Elements;

public interface IScraperRU {
    Elements parseTableHtml(String url);
    String extractFileNameWithoutExtension(String url);
}
