package io.guerra.fernando.weatherapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service
public class WeatherService {

    @Inject
    private RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    public String getWeather() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "http://weather-service/weather";
        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class), throwable -> unknown());
    }

    private String unknown() {
        return "unknown";
    }
}
