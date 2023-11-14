package com.assign.utilities.service;

import com.assign.utilities.pojo.TempAndWindData;
import reactor.core.publisher.Mono;

public interface WindAndTemperatureService {

    Mono<TempAndWindData> fetchTempAndWindData(String cityName);
}
