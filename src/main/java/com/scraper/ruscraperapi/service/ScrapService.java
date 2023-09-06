package com.scraper.ruscraperapi.service;

import com.scraper.ruscraperapi.data.meals.Meal;
import com.scraper.ruscraperapi.data.meals.MealOption;
import com.scraper.ruscraperapi.data.meals.ResponseMenu;
import com.scraper.ruscraperapi.data.meals.ResponseMenuFactory;
import com.scraper.ruscraperapi.data.ru.Ru;
import com.scraper.ruscraperapi.data.ru.RuFactory;
import com.scraper.ruscraperapi.scrap.ScraperRU;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.util.Optional;

@Service
public class ScrapService {

    private final ResponseMenuFactory responseMenuFactory;
    private final ScraperRU scraperRU;

    private final RuFactory ruFactory;

    public ScrapService(ResponseMenuFactory responseMenuFactory, RuFactory ruFactory, ScraperRU scraperRU) {
        this.responseMenuFactory = responseMenuFactory;
        this.scraperRU = scraperRU;
        this.ruFactory = ruFactory;
    }

    public Optional<Object> getMenuToday(String ruCode) {
        Ru ru = ruFactory.createRu(ruCode);
        ResponseMenu responseMenu = responseMenuFactory.createResponseMenu(ru);
        scraperRU.connect(ru.getUrl());
        Elements mealRows = scraperRU.parseTableHtml();
        Meal mealPeriod = null;

        for (Element element : mealRows) {
            Element tdElement = element.select("td").first();

            if (tdElement.text().equalsIgnoreCase("CAFÉ DA MANHÃ") || tdElement.text().equalsIgnoreCase("ALMOÇO") || tdElement.text().equalsIgnoreCase("JANTAR")) {
                if (mealPeriod != null) {
                    responseMenu.addMeal(mealPeriod);
                }
                mealPeriod = new Meal();
                mealPeriod.setTitle(tdElement.text());
                continue;
            }

            if (mealPeriod != null) {
                String[] contentFromRow = tdElement.html().split("<br>");
                for (String contentPart : contentFromRow) {
                    MealOption mealOption = new MealOption();

                    Document contentDocument = Jsoup.parse(contentPart);
                    String text = contentDocument.text();

                    mealOption.setName(text);

                    Elements imgElements = contentDocument.select("img");
                    for (Element imgElement : imgElements) {
                        String src = imgElement.attr("src");
                        String imageName = scraperRU.extractFileNameWithoutExtension(src);
                        mealOption.addIcon(imageName);
                    }

                    mealPeriod.addMealOption(mealOption);
                }
            }
        }

        if (mealPeriod != null) {
            responseMenuFactory.addMealToResponseMenu(responseMenu, mealPeriod);
        }

        return Optional.of(responseMenu);
    }
}