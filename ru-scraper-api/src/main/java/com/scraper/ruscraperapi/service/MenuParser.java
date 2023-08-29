package com.scraper.ruscraperapi.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuParser {
    public static Map<String, List<MenuItem>> parseMenuByMeal(Element menuElement) {
        Map<String, List<MenuItem>> menuByMeal = new HashMap<>();

        Elements rows = menuElement.select("table tbody tr");  // Select all table rows
        String currentMeal = null;
        List<MenuItem> currentMealItems = null;

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (!cells.isEmpty()) {
                String cellText = cells.get(0).text();
                if (cellText.toUpperCase().contains("CAFÉ DA MANHÃ")
                        || cellText.toUpperCase().contains("ALMOÇO")
                        || cellText.toUpperCase().contains("JANTAR")) {
                    currentMeal = cellText;
                    currentMealItems = new ArrayList<>();
                    menuByMeal.put(currentMeal, currentMealItems);
                } else {
                    String title = cellText;
                    Elements iconElements = cells.select("img[src]");
                    List<String> icons = new ArrayList<>();
                    for (Element iconElement : iconElements) {
                        String iconSrc = iconElement.attr("src");
                        icons.add(iconSrc.substring(iconSrc.lastIndexOf('/') + 1, iconSrc.lastIndexOf('.')));
                    }
                    if (currentMealItems != null) {
                        currentMealItems.add(new MenuItem(title, icons));
                    }
                }
            }
        }

        return menuByMeal;
    }
}
