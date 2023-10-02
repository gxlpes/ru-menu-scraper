package com.scraper.ruscraperapi.factory.responseMenu;

import com.scraper.ruscraperapi.data.meal.Meal;
import com.scraper.ruscraperapi.data.response.ResponseMenu;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ResponseMenuFactory implements IResponseMenuFactory {

    @Override
    public ResponseMenu createResponseMenu(String ruCode) {
        ResponseMenu responseMenu = new ResponseMenu();
        responseMenu.setRuCode(ruCode);
        responseMenu.setDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return responseMenu;
    }

    public void addMealToResponseMenu(ResponseMenu responseMenu, Meal meal) {
        responseMenu.addMeal(meal);
    }
}
