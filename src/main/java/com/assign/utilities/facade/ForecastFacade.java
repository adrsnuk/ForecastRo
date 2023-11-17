package com.assign.utilities.facade;

import com.assign.utilities.pojo.ForecastAverage;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface ForecastFacade {

    Mono<List<ForecastAverage>> computeForecast(Set<String> city);
}
