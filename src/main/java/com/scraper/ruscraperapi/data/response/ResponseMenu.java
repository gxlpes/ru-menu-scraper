package com.scraper.ruscraperapi.data.response;

import com.scraper.ruscraperapi.data.meal.MealOption;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseMenu {

    private ZonedDateTime date;

    private String ruCode;
    private Map<String, List<MealOption>> meals;
    private List<String> served;

    public ResponseMenu() {
        this.meals = new HashMap<>();
    }

    public String getRuCode() {
        return ruCode;
    }

    public void setRuCode(String ruCode) {
        this.ruCode = ruCode;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Map<String, List<MealOption>> getMeals() {
        return meals;
    }

    public void setMeals(Map<String, List<MealOption>> meals) {
        this.meals = meals;
    }

    public List<String> getServed() {
        return served;
    }

    public void setServed(List<String> served) {
        this.served = served;
    }

    public void addMeal(String mealPeriod, List<MealOption> mealOptions) {
        this.meals.put(mealPeriod, mealOptions);
    }

}
