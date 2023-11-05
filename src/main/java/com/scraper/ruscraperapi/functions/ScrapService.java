package com.scraper.ruscraperapi.functions;

import com.scraper.ruscraperapi.data.meal.Meal;
import com.scraper.ruscraperapi.data.meal.MealOption;
import com.scraper.ruscraperapi.data.response.ResponseMenu;
import com.scraper.ruscraperapi.exception.types.RuMenuNotFound;
import com.scraper.ruscraperapi.factory.responseMenu.ResponseMenuFactory;
import com.scraper.ruscraperapi.scrap.ScraperRU;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScrapService implements IScrapService {

    private final ResponseMenuFactory responseMenuFactory;
    private final ScraperRU scraperRU;

    public ScrapService(ResponseMenuFactory responseMenuFactory, ScraperRU scraperRU) {
        this.responseMenuFactory = responseMenuFactory;
        this.scraperRU = scraperRU;
    }

    public ResponseMenu scrape(String ruCode) {
        ResponseMenu responseMenu = responseMenuFactory.createResponseMenu(ruCode);
        Elements mealRows = scraperRU.parseTableHtml(ruCode);

        if (mealRows == null) {
            throw new RuMenuNotFound("Menu not found with this date " + LocalDateTime.now());
        }

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

        return responseMenu;
    }
}