package com.scraper.ruscraperapi.scrap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ConnectionManager {

    public Document connect(String websiteURL) {
        try {
            return Jsoup.connect(websiteURL).get();
        } catch (IOException e) {
            System.err.println("Failed to retrieve menu: " + e.getMessage());
            return null;
        }
    }
}