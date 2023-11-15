package com.scraper.ruscraperapi.scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ScraperRU implements IScraperRU {

    private Document htmlDocument;
    private String localDate;

    private void connectScraper(String webURL) {
        try {
            this.htmlDocument = Jsoup.connect(webURL).get();
        } catch (IOException e) {
            System.err.println("Failed to retrieve menu: " + e.getMessage());
        }
    }

    private String getUrlFromRu(String ruCode) {
        return switch (ruCode) {
            case "BOT" -> "https://pra.ufpr.br/ru/cardapio-ru-jardim-botanico/";
            case "POL" -> "https://pra.ufpr.br/ru/ru-centro-politecnico/";
            default -> "No URL were found attached with the code " + ruCode;
        };
    }

    @Override
    public Elements parseTableHtml(String ruCode) {
        String ruUrl = getUrlFromRu(ruCode);
        if (htmlDocument == null) connectScraper(ruUrl);
        connectScraper(ruUrl);
        this.localDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM"));
        Element titleContainingDate = htmlDocument.selectFirst("p:contains(" + localDate + ")");
        if (titleContainingDate == null) return null;
        Element menuFromWeekday = titleContainingDate.nextElementSibling();
        return menuFromWeekday.select("table tbody tr");
    }

    @Override
    public String extractFileNameWithoutExtension(String url) {
        if (htmlDocument == null) {
            throw new IllegalStateException("parseTableHtml must be called before extractFileNameWithoutExtension.");
        }

        int lastIndexOfSlash = url.lastIndexOf('/');
        if (lastIndexOfSlash != -1) {
            String extractedPart = url.substring(lastIndexOfSlash + 1);
            int indexOfDot = extractedPart.lastIndexOf('.');
            if (indexOfDot != -1) return extractedPart.substring(0, indexOfDot);
        }
        return null;
    }

}