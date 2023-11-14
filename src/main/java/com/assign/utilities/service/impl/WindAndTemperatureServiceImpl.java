package com.assign.utilities.service.impl;

import com.assign.utilities.pojo.TempAndWindData;
import com.assign.utilities.service.WindAndTemperatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WindAndTemperatureServiceImpl implements WindAndTemperatureService {

    @Override
    public Mono<TempAndWindData> fetchTempAndWindData(String cityName) {
        return WebClient.create(toPath(cityName))
                .get()
                .exchangeToMono(response -> {

                    if (response.statusCode().is2xxSuccessful()) {
                        return response
                                .bodyToMono(TempAndWindData.class)
                                .map(tempAndWindData ->
                                        {
                                            tempAndWindData.setCityName(cityName);
                                            log.info("Fetched data: {}", tempAndWindData);
                                            return tempAndWindData;
                                        }
                                );

                    } else {
                        log.info("Fetched data: {}", new TempAndWindData(cityName));
                        return Mono.just(new TempAndWindData(cityName));
                    }
                });
    }

    private String toPath(String cityName) {
        String path = "https://998d8129-2264-4a98-a92e-ba8bde4a4d1c.mock.pstmn.io/" + cityName;
        log.info("Fetching data from: {}", path);
        return path;
    }
}
