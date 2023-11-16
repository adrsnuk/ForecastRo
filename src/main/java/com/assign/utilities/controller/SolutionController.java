package com.assign.utilities.controller;

import com.assign.utilities.client.ForecastClient;
import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.service.ForecastAverageCalculatorService;
import com.assign.utilities.service.ForecastCsvWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolutionController {

    private final ForecastClient forecastClient;

    private final ForecastAverageCalculatorService forecastAverageCalculatorService;

    private final ForecastCsvWriterService forecastCsvWriterService;
    @GetMapping("/weather")
    public Mono<List<ForecastAverage>> computeWeatherByCities(@RequestParam Set<String> city) {
        Set<String> acceptedCities = Set.of("Cluj-Napoca", "Bucuresti", "Timisoara", "Constanta", "Baia-Mare", "Arad");

        Set<String> queriedCities = acceptedCities.stream()
                .filter(city::contains)
                .collect(Collectors.toSet());

        return Flux.fromIterable(queriedCities)
                .flatMap(forecastClient::fetchForecastForCity)
                .map(forecastAverageCalculatorService::computeAverageForecast)
                .sort(Comparator.comparing(ForecastAverage::getCityName))
                .collectList()
                .map(forecastCsvWriterService::writeForecastToCsv);
    }
}
