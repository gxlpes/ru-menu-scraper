package com.scraper.ruscraperapi.service;

import com.scraper.ruscraperapi.scrap.ConnectionManager;
import com.scraper.ruscraperapi.scrap.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ScrapService {
    ConnectionManager connectionManager = new ConnectionManager();

    public Optional<Object> getMenuToday() {
        Document document = connectionManager.connect(Utils.getUrlFromRu("bot"));

        LocalDate today = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String outputDate = today.format(formatter);

        System.out.println(outputDate);

        Element titleContainingDate = document.selectFirst("p:contains(" + outputDate + ")");

        System.out.println(titleContainingDate);

        if (titleContainingDate == null) return Optional.empty();

        Element menuFromWeekday = titleContainingDate.nextElementSibling();

        System.out.println(menuFromWeekday);

        return Optional.of("opa");
    }
}
