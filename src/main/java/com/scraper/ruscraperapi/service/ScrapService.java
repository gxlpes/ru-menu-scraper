package com.scraper.ruscraperapi.service;

import com.scraper.ruscraperapi.data.meals.Meal;
import com.scraper.ruscraperapi.data.meals.MealOption;
import com.scraper.ruscraperapi.data.meals.ResponseMenu;
import com.scraper.ruscraperapi.scrap.ConnectionManager;
import com.scraper.ruscraperapi.scrap.ScraperRU;
import com.scraper.ruscraperapi.scrap.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ScrapService {
    ConnectionManager connectionManager = new ConnectionManager();

    public Optional<Object> getMenuToday() {
        ScraperRU scraperRU = new ScraperRU();
        Document document = connectionManager.connect(Utils.getUrlFromRu("bot"));
        String dateFormatted = scraperRU.dateFormatted();

        System.out.println(dateFormatted);

        Element titleContainingDate = document.selectFirst("p:contains(" + dateFormatted + ")");

        if (titleContainingDate == null) return Optional.empty();

        Element menuFromWeekday = titleContainingDate.nextElementSibling();


        Elements mealRows = menuFromWeekday.select("table tbody tr");
        ResponseMenu responseMenu = new ResponseMenu();

        System.out.println(mealRows);

        Meal currentMeal = null;

        for (Element element : mealRows) {
            Element tdElement = element.select("td").first();

            if (tdElement.text().equalsIgnoreCase("CAFÉ DA MANHÃ") || tdElement.text().equalsIgnoreCase("ALMOÇO") || tdElement.text().equalsIgnoreCase("JANTAR")) {

                // Create a new Meal object when a new meal title is encountered.
                currentMeal = new Meal();
                currentMeal.setTitle(tdElement.text());
                responseMenu.addMeal(currentMeal);
                continue;
            }

            if (currentMeal != null) {
                // Process meal options for the current meal.
                String[] contentArray = tdElement.html().split("<br>");
                for (String contentPart : contentArray) {
                    MealOption mealOption = new MealOption();

                    Document contentDocument = Jsoup.parse(contentPart);
                    String text = contentDocument.text();

                    mealOption.setName(text);

                    System.out.println(text);

                    Elements imgElements = contentDocument.select("img");
                    for (Element imgElement : imgElements) {
                        String src = imgElement.attr("src");
                        String imageName = extractFileNameWithoutExtension(src);
                        mealOption.addIcon(imageName);
                        System.out.println("Image name: " + imageName);
                    }

                    currentMeal.addMealOption(mealOption);
                }
            }
            responseMenu.addMeal(currentMeal);

        }

        return Optional.of(responseMenu);


    }


    public static String extractFileNameWithoutExtension(String url) {
        int lastIndexOfSlash = url.lastIndexOf('/');
        if (lastIndexOfSlash != -1) {
            String extractedPart = url.substring(lastIndexOfSlash + 1);
            int indexOfDot = extractedPart.lastIndexOf('.');
            if (indexOfDot != -1) {
                return extractedPart.substring(0, indexOfDot);
            }
        }
        return null; // Return null if the URL format is not as expected
    }

}
