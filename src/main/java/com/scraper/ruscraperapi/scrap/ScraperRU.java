package com.scraper.ruscraperapi.scrap;

import com.scraper.ruscraperapi.data.ru.Ru;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ScraperRU {
    private Document htmlDocument;
    private String localDate;


    public void connect(String webURL) {
        this.localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM"));
        try {
            this.htmlDocument = Jsoup.connect(webURL).get();
        } catch (IOException e) {
            System.err.println("Failed to retrieve menu: " + e.getMessage());
        }
    }


    public Elements parseTableHtml() {
        Element titleContainingDate = htmlDocument.selectFirst("p:contains(" + localDate + ")");
        if (titleContainingDate == null) {
            return null;
        }
        Element menuFromWeekday = titleContainingDate.nextElementSibling();
        return menuFromWeekday.select("table tbody tr");
    }

    public String extractFileNameWithoutExtension(String url) {
        int lastIndexOfSlash = url.lastIndexOf('/');
        if (lastIndexOfSlash != -1) {
            String extractedPart = url.substring(lastIndexOfSlash + 1);
            int indexOfDot = extractedPart.lastIndexOf('.');
            if (indexOfDot != -1) {
                return extractedPart.substring(0, indexOfDot);
            }
        }
        return null;
    }

}