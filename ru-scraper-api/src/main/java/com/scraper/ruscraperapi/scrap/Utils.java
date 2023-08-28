package com.scraper.ruscraperapi.scrap;

public final class Utils {

    private Utils() {
    }

    public static String getUrlFromRu(String ru) {
        switch (ru) {
            case "bot":
                return "https://pra.ufpr.br/ru/cardapio-ru-jardim-botanico/";
            case "pol":
                return "https://pra.ufpr.br/ru/ru-centro-politecnico/";
            default:
                return "Not found";
        }
    }

    public static String convertRuName(String ru) {
        switch (ru) {
            case "bot":
                return "Botânico";
            case "pol":
                return "Politécnico";
            default:
                return "Not found";
        }
    }

}