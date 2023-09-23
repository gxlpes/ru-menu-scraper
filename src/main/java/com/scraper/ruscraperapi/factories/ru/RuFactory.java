package com.scraper.ruscraperapi.factories.ru;

import com.scraper.ruscraperapi.data.ru.Ru;
import org.springframework.stereotype.Component;

@Component
public class RuFactory implements IRuFactory {

    private final Ru ru;

    public RuFactory(Ru ru) {
        this.ru = ru;
    }

    @Override
    public Ru createRuBasedByCode(String ruCode) {
        String url = getUrlFromRu(ruCode);
        String name = convertRuName(ruCode);
        ru.setUrl(url);
        ru.setName(name);
        ru.setCode(ruCode);
        return ru;
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
