package com.scraper.ruscraperapi.service;

import com.scraper.ruscraperapi.dto.MenuMeals;
import com.scraper.ruscraperapi.scrap.ConnectionManager;
import com.scraper.ruscraperapi.scrap.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.scraper.ruscraperapi.service.MenuParser.parseMenuByMeal;

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

        Map<String, List<MenuItem>> menuByMeal = parseMenuByMeal(menuFromWeekday);

        for (Map.Entry<String, List<MenuItem>> entry : menuByMeal.entrySet()) {
            System.out.println("Meal: " + entry.getKey());
            List<MenuItem> menuItems = entry.getValue();
            for (MenuItem menuItem : menuItems) {
                System.out.println("  Title: " + menuItem.getTitle());
                System.out.println("  Icons: " + menuItem.getIcons());
            }
            System.out.println();
        }

        return Optional.of("opa");
    }


}
