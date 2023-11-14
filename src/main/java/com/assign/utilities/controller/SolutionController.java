package com.assign.utilities.controller;

import com.assign.utilities.pojo.AverageForecast;
import com.assign.utilities.service.AverageForecastService;
import com.assign.utilities.service.ForecastCsvWriterService;
import com.assign.utilities.service.WindAndTemperatureService;
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

    private final WindAndTemperatureService windAndTemperatureService;

    private final AverageForecastService averageForecastService;

    private final ForecastCsvWriterService forecastCsvWriterService;
    @GetMapping("/weather")
    public Mono<List<AverageForecast>> computeWeatherByCities(@RequestParam Set<String> city) {
        Set<String> acceptCities = Set.of("Cluj-Napoca", "Bucuresti", "Timisoara", "Constanta", "Baia-Mare", "Arad");

        Set<String> searchCities = acceptCities.stream()
                .filter(city::contains)
                .collect(Collectors.toSet());

        return Flux.fromIterable(searchCities)
                .flatMap(windAndTemperatureService::fetchTempAndWindData)
                .map(averageForecastService::computeAverageForecast)
                .sort(Comparator.comparing(AverageForecast::getCityName))
                .collectList()
                .map(forecastCsvWriterService::writeForecastToCsv);
    }
}
