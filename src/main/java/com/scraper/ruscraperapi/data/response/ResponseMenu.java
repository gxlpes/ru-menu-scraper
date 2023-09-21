package com.scraper.ruscraperapi.data.response;

import com.scraper.ruscraperapi.data.meal.Meal;
import com.scraper.ruscraperapi.data.ru.Ru;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseMenu {

    private ZonedDateTime date;
    private List<String> served;
    private Ru ru;
    private List<Meal> meals;

    public ResponseMenu() {
        this.served = new ArrayList<>(); ;
        this.meals = new ArrayList<>();
    }

    public List<String> getServed() {
        return served;
    }

    public void setServed(List<String> served) {
        this.served = served;
    }

    public void addServed(String meal) {served.add(meal);}

    public void setRu(Ru ru) {
        this.ru = ru;
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

    public Ru getRu() {
        return this.ru;
    }
}