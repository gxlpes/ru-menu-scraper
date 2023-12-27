package com.scraper.ruscraperapi.factory;

import com.scraper.ruscraperapi.data.meal.MealOption;
import com.scraper.ruscraperapi.data.response.ResponseMenu;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Component
public class ResponseMenuBuilder implements IResponseMenuBuilder {

    @Override
    public ResponseMenu createResponseMenu(String ruCode, Map<String, List<MealOption>> meals) {
        ResponseMenu responseMenu = new ResponseMenu();
        responseMenu.setDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        responseMenu.setRuCode(ruCode);
        responseMenu.setMeals(meals);
        return responseMenu;
    }

}
