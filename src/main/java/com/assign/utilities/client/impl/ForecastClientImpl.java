package com.assign.utilities.client.impl;

import com.assign.utilities.client.ForecastClient;
import com.assign.utilities.pojo.ForecastData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Component
public class ForecastClientImpl implements ForecastClient {

    @Value("${forecast_api_url}")
    private String forecast_api_url;

    @Override
    public Mono<ForecastData> fetchForecastForCity(String cityName) {
        return WebClient.create(toPath(cityName))
                .get()
                .exchangeToMono(response -> {

                    if (response.statusCode().is2xxSuccessful()) {
                        return response
                                .bodyToMono(ForecastData.class)
                                .map(tempAndWindData ->
                                        {
                                            tempAndWindData.setCityName(cityName);
                                            log.info("[200] Fetched data: {}", tempAndWindData);
                                            return tempAndWindData;
                                        }
                                );

                    } else {
                        ForecastData failedData = ForecastData.builder()
                                .cityName(cityName)
                                .wind("")
                                .temperature("")
                                .description("")
                                .forecast(Collections.emptyList())
                                .build();

                        log.info("[{}] Fetched data: {}", response.statusCode().value(), failedData);
                        return Mono.just(failedData);
                    }
                });
    }

    private String toPath(String cityName) {
        String path = forecast_api_url + cityName;
        log.info("Fetching data from: {}", path);
        return path;
    }
}
