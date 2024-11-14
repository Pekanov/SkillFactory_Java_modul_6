package org.example.skillfactory_java_modul_6.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.skillfactory_java_modul_6.dto.RequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${key.yandex_api_key}")
    private String YApiSecret;

    @Value("${request.yandex_weather_address}")
    private String YApiAddress;

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String getWeatherAll(RequestDto requestDto) {

        HttpRequest request = getRequest(requestDto);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());  // Выводим тело ответа
            return response.body();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public String getWeatherShort(RequestDto requestDto) {

        HttpRequest request = getRequest(requestDto);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode jsonResponse = objectMapper.readTree(response.body());
            JsonNode currentTemperature = jsonResponse.at("/fact/temp");
            JsonNode avgDayTemperature = jsonResponse.at("/forecasts/0/parts/day/temp_avg");

            return String.format("Current temperature: %s, Average day temperature: %s",
                    currentTemperature.asText(), avgDayTemperature.asText());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error fetching weather data", e);
        }

    }

    private HttpRequest getRequest(RequestDto requestDto) {
        String url = String.format(Locale.US, "%s?lat=%f&lon=%f", YApiAddress, requestDto.getLatitude(), requestDto.getLongitude());

        HttpClient client = HttpClient.newHttpClient();
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Yandex-Weather-Key", YApiSecret)
                .GET()
                .build();


    }


}
