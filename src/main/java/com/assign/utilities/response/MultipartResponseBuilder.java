package com.assign.utilities.response;

import com.assign.utilities.pojo.ForecastAverage;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;

import java.util.List;

public interface MultipartResponseBuilder {

    Flux<Part> buildJsonAndCsvResponse(List<ForecastAverage> forecastAverages);
}
