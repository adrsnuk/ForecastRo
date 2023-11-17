package com.assign.utilities.controller;

import com.assign.utilities.facade.ForecastFacade;
import com.assign.utilities.response.MultipartResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SolutionController {

    private final ForecastFacade forecastFacade;

    private final MultipartResponseBuilder multipartResponseBuilder;

    @GetMapping(value = "/weather", produces = "multipart/mixed")
    public Flux<Part> computeForecastForCities(@RequestParam Set<String> city) {
      return  forecastFacade.computeForecast(city)
                .flatMapMany(multipartResponseBuilder::buildJsonAndCsvResponse);

    }
}
