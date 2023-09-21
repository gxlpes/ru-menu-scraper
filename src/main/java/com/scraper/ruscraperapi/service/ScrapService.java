package com.scraper.ruscraperapi.service;

import com.scraper.ruscraperapi.data.meal.Meal;
import com.scraper.ruscraperapi.data.meal.MealOption;
import com.scraper.ruscraperapi.data.response.ResponseMenu;
import com.scraper.ruscraperapi.data.response.ResponseMenuFactory;
import com.scraper.ruscraperapi.data.ru.Ru;
import com.scraper.ruscraperapi.data.ru.RuFactory;
import com.scraper.ruscraperapi.scrap.ScraperRU;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;
import java.util.function.Function;

public class ScrapService implements Function<String, Object> {

    private final ResponseMenuFactory responseMenuFactory;
    private final RuFactory ruFactory;
    private final ScraperRU scraperRU;

    public ScrapService(ResponseMenuFactory responseMenuFactory, RuFactory ruFactory, ScraperRU scraperRU) {
        this.responseMenuFactory = responseMenuFactory;
        this.ruFactory = ruFactory;
        this.scraperRU = scraperRU;
    }

    @Override
    public Optional<Object> apply(String ruCode) {
        Ru ru = ruFactory.createRuBasedByCode(ruCode);
        ResponseMenu responseMenu = responseMenuFactory.createResponseMenu(ru);
        Elements mealRows = scraperRU.parseTableHtml(ru.getUrl());
        Meal mealPeriod = null;

        for (Element element : mealRows) {
            Element tdElement = element.select("td").first();
            String mealTitle = tdElement.text();

            if (mealTitle.equalsIgnoreCase("CAFÉ DA MANHÃ") || mealTitle.equalsIgnoreCase("ALMOÇO") || mealTitle.equalsIgnoreCase("JANTAR")) {
                if (mealPeriod != null) {
                    responseMenu.addMeal(mealPeriod);
                }
                mealPeriod = new Meal();
                mealPeriod.setTitle(mealTitle);
                responseMenu.addServed(mealTitle);

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