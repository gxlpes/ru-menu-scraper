package com.scraper.ruscraperapi.data.response;

import com.scraper.ruscraperapi.data.meal.Meal;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseMenu {

    private ZonedDateTime date;
    private List<String> served;
    private List<Meal> meals;
    private String ruCode;

    public ResponseMenu() {
        this.served = new ArrayList<>();
        this.meals = new ArrayList<>();
    }

    public List<String> getServed() {
        return served;
    }

    public void setServed(List<String> served) {
        this.served = served;
    }

    public void addServed(String meal) {
        served.add(meal);
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

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

}