package com.scraper.ruscraperapi.factory.responseMenu;

import com.scraper.ruscraperapi.data.response.ResponseMenu;
import com.scraper.ruscraperapi.data.ru.Ru;

public interface IResponseMenuFactory {
    ResponseMenu createResponseMenu(String ruCode);
}
