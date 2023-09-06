package com.scraper.ruscraperapi.data.ru;

public class Ru {

    private String url;
    private String name;
    private String code;

    public Ru(String code, String url, String name) {
        this.code = code;
        this.url = url;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
