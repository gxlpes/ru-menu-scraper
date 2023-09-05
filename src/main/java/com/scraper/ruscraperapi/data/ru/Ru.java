package com.scraper.ruscraperapi.data.ru;

public class Ru {

    private String url;
    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Ru(String ruCode) {
        this.url = getUrlFromRu(ruCode);
        this.name = convertRuName(ruCode);
        this.code = ruCode;
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

    public String getUrlFromRu(String ruCode) {
        return switch (ruCode) {
            case "bot" -> "https://pra.ufpr.br/ru/cardapio-ru-jardim-botanico/";
            case "pol" -> "https://pra.ufpr.br/ru/ru-centro-politecnico/";
            default -> "Not found";
        };
    }

    public String convertRuName(String ruCode) {
        return switch (ruCode) {
            case "bot" -> "Botânico";
            case "pol" -> "Politécnico";
            default -> "Not found";
        };
    }
}
