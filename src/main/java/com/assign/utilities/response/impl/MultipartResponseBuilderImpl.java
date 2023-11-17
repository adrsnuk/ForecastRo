package com.assign.utilities.response.impl;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.response.MultipartResponseBuilder;
import com.assign.utilities.response.parts.ForecastCsvPart;
import com.assign.utilities.response.parts.ForecastJsonPart;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.codec.multipart.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MultipartResponseBuilderImpl implements MultipartResponseBuilder {

    private final ObjectMapper objectMapper;

    @Override
    public Flux<Part> buildJsonAndCsvResponse(List<ForecastAverage> forecastAverages) {

        return Flux.just(
                createJsonPart(forecastAverages),
                createCsvPart()
        );
    }

    private Part createCsvPart(){
        return new ForecastCsvPart();
    }

    private Part createJsonPart(List<ForecastAverage> forecastAverages){
        return new ForecastJsonPart(forecastAverages, objectMapper);
    }
}

