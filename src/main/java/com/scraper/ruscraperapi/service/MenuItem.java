package com.scraper.ruscraperapi.service;

import java.util.List;

public class MenuItem {
    private String title;
    private List<String> icons;

    public MenuItem(String title, List<String> icons) {
        this.title = title;
        this.icons = icons;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getIcons() {
        return icons;
    }
}
