package com.assign.utilities.facade.impl;

import com.assign.utilities.client.ForecastClient;
import com.assign.utilities.facade.ForecastFacade;
import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.service.ForecastAverageCalculatorService;
import com.assign.utilities.service.ForecastCsvWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ForecastFacadeImpl implements ForecastFacade {

    private final ForecastClient forecastClient;

    private final ForecastAverageCalculatorService forecastAverageCalculatorService;

    private final ForecastCsvWriterService forecastCsvWriterService;

    @Override
    public Mono<List<ForecastAverage>> computeForecast(Set<String> city) {
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
