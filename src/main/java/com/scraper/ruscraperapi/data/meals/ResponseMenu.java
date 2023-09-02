package com.scraper.ruscraperapi.data.meals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResponseMenu {

    private LocalDate date;
    private List<Meal> meals;

    public ResponseMenu() {
        this.meals = new ArrayList<>();
        this.date = LocalDate.now().plusDays(2);
    }

    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

}
