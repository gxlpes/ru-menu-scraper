package com.scraper.ruscraperapi.data.ru;

public class RuFactory implements RuFactoryMethods {

    @Override
    public Ru createRuBasedByCode(String ruCode) {
        String url = getUrlFromRu(ruCode);
        String name = convertRuName(ruCode);
        return new Ru(ruCode, url, name);
    }

    private String getUrlFromRu(String ruCode) {
        return switch (ruCode) {
            case "bot" -> "https://pra.ufpr.br/ru/cardapio-ru-jardim-botanico/";
            case "pol" -> "https://pra.ufpr.br/ru/ru-centro-politecnico/";
            default -> "Not found";
        };
    }

    private String convertRuName(String ruCode) {
        return switch (ruCode) {
            case "bot" -> "Botânico";
            case "pol" -> "Politécnico";
            default -> "Not found";
        };
    }
}
