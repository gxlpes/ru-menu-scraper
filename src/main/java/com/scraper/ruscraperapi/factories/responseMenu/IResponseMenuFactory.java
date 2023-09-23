package com.scraper.ruscraperapi.factories.responseMenu;

import com.scraper.ruscraperapi.data.response.ResponseMenu;
import com.scraper.ruscraperapi.data.ru.Ru;

public interface IResponseMenuFactory {
    ResponseMenu createResponseMenu(Ru ru);
}
