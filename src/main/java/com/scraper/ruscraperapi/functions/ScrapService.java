package com.scraper.ruscraperapi.functions;

import com.scraper.ruscraperapi.data.meal.MealOption;
import com.scraper.ruscraperapi.data.response.ResponseMenu;
import com.scraper.ruscraperapi.exception.types.RuMenuNotFound;
import com.scraper.ruscraperapi.factory.responseMenu.ResponseMenuFactory;
import com.scraper.ruscraperapi.scrap.ScraperRU;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapService implements IScrapService {

    @Value("${RU_CODE}")
    private String ruKey;
    private final ResponseMenuFactory responseMenuFactory;
    private final ScraperRU scraperRU;

    public ScrapService(ResponseMenuFactory responseMenuFactory, ScraperRU scraperRU) {
        this.responseMenuFactory = responseMenuFactory;
        this.scraperRU = scraperRU;
    }

    public ResponseMenu scrape() {
        ResponseMenu responseMenu = responseMenuFactory.createResponseMenu(ruKey);
        Elements mealRows = scraperRU.parseTableHtml(ruKey);

        if (mealRows == null) {
            throw new RuMenuNotFound("Menu not found with this date " + LocalDateTime.now());
        }

        Map<String, List<MealOption>> meals = new HashMap<>();
        String mealPeriodTitle = null;
        List<MealOption> mealOptions = new ArrayList<>();

        for (Element element : mealRows) {
            Element tdElement = element.select("td").first();
            String mealTitle = mapMealTitle(tdElement.text());

            if (mealTitle != null) {
                if (mealPeriodTitle != null) {
                    if (!mealOptions.isEmpty()) {
                        meals.put(mealPeriodTitle, new ArrayList<>(mealOptions));
                    }
                }

                mealOptions = new ArrayList<>();
                mealPeriodTitle = mealTitle;
                responseMenu.addServed(mealTitle);
                continue;
            }

            String[] contentFromRow = tdElement.html().split("<br>");
            for (String contentPart : contentFromRow) {
                MealOption mealOption = new MealOption();

                Document contentDocument = Jsoup.parse(contentPart);
                String text = contentDocument.text();

                if (!text.isEmpty()) {
                    mealOption.setName(text);

                    Elements imgElements = contentDocument.select("img");
                    for (Element imgElement : imgElements) {
                        String src = imgElement.attr("src");
                        String imageName = scraperRU.extractFileNameWithoutExtension(src);
                        mealOption.addIcon(imageName);
                    }

                    mealOptions.add(mealOption);
                }
            }
        }

        if (mealPeriodTitle != null && !mealOptions.isEmpty()) {
            meals.put(mealPeriodTitle, new ArrayList<>(mealOptions));
        }

        responseMenu.setMeals(meals);
        return responseMenu;
    }

    private String mapMealTitle(String originalTitle) {
        return switch (originalTitle.toUpperCase()) {
            case "CAFÉ DA MANHÃ" -> "breakfast";
            case "ALMOÇO" -> "lunch";
            case "JANTAR" -> "dinner";
            default -> null;
        };
    }
}
