package com.scraper.ruscraperapi.data.meal;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    private String title;
    private List<MealOption> options;

    public Meal() {
        this.options = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MealOption> getOptions() {
        return options;
    }

    public void setOptions(List<MealOption> options) {
        this.options = options;
    }

    public void addMealOption (MealOption mealOption) {
        options.add(mealOption);
    }

}
