package com.assign.utilities.client;

import com.assign.utilities.pojo.ForecastData;
import reactor.core.publisher.Mono;

public interface ForecastClient {
    Mono<ForecastData> fetchForecastForCity(String cityName);
}
