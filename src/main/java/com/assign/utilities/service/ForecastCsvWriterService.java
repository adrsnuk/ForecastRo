package com.assign.utilities.service;

import com.assign.utilities.pojo.ForecastAverage;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ForecastCsvWriterService {

    Mono<List<ForecastAverage>> writeAsync(List<ForecastAverage> averages);
}
