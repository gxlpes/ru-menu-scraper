package com.scraper.ruscraperapi.scrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScraperRU {
    private Document htmlDocument;


    public void connect() {
        try {
            htmlDocument = Jsoup.connect("https://pra.ufpr.br/ru/ru-centro-politecnico/").get();
        } catch (IOException e) {
            System.err.println("Failed to retrieve menu: " + e.getMessage());
        }
    }

    public String dateFormatted() {
        return LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    }

    public boolean menuExists(String formattedDate) {
        Element titleContainingDate = htmlDocument.selectFirst("p:contains(" + formattedDate + ")");
        System.out.println("testing here");
        System.out.println(titleContainingDate);
        return titleContainingDate != null;
    }

    public Elements parseTableHtml(Element tableHeader) {
        Element menuFromWeekday = tableHeader.nextElementSibling();
        return menuFromWeekday.select("table tbody tr");
    }

    public Document getHtmlDocument() {
        return htmlDocument;
    }

    public void setHtmlDocument(Document htmlDocument) {
        this.htmlDocument = htmlDocument;
    }
}
