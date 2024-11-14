package org.example.skillfactory_java_modul_6.controller;

import org.example.skillfactory_java_modul_6.dto.RequestDto;
import org.example.skillfactory_java_modul_6.service.WeatherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/getWeatherAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeatherAll(@RequestBody RequestDto requestDTO) {
        return weatherService.getWeatherAll(requestDTO);
    }

    @GetMapping(value = "/getWeatherShort", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getWeatherShort(@RequestBody RequestDto requestDTO) {
        return weatherService.getWeatherShort(requestDTO);
    }

}
