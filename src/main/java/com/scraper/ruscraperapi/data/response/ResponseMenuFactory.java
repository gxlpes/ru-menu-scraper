package com.scraper.ruscraperapi.data.response;

import com.scraper.ruscraperapi.data.meal.Meal;
import com.scraper.ruscraperapi.data.ru.Ru;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class ResponseMenuFactory implements ResponseMenuFactoryMethods {

    @Override
    public ResponseMenu createResponseMenu(Ru ru) {
        ResponseMenu responseMenu = new ResponseMenu();
        responseMenu.setRu(ru);
        responseMenu.setDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return responseMenu;
    }

    public void addMealToResponseMenu(ResponseMenu responseMenu, Meal meal) {
        responseMenu.addMeal(meal);
    }
}
