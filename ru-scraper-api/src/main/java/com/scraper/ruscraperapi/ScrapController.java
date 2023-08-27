package com.scraper.ruscraperapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/menu")
public class ScrapController {

    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    @GetMapping("/{ru}/{date}")
    public ResponseEntity<Object> getMenu(@PathVariable(value = "ru") String ru, @PathVariable(value = "date") String date) {
        Optional<MenuDto> menuDtoOptional = scrapService.getMenu(ru, date);

        if (menuDtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weekday was not found in the menu");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(menuDtoOptional);
        }
    }
}
