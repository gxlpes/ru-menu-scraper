package com.scraper.ruscraperapi.data.meals;

import com.scraper.ruscraperapi.data.ru.Ru;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseMenu {

    private ZonedDateTime date;

    private Ru ru;
    private List<Meal> meals;

    public ResponseMenu() {
        this.meals = new ArrayList<>(); // Initialize the meals list in the constructor.
    }

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