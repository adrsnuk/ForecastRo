package com.assign.utilities;

import com.assign.utilities.dto.TempAndWindCityData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.multipart.DiskFileUpload.prefix;


@RestController
@RequestMapping("/api")
public class SolutionController {

    @GetMapping("/weather")
    public Mono<List<AverageForecast>> computeWeatherByCities(@RequestParam Set<String> city) {
        Set<String> acceptCities = Set.of("Cluj-Napoca", "Bucuresti", "Timisoara", "Constanta", "Baia-Mare", "Arad");

        Set<String> searchCities = acceptCities.stream()
                .filter(city::contains)
                .collect(Collectors.toSet());

        return Flux.fromIterable(searchCities)
                .flatMap(this::handleRemoteApiCall)
                .map(AverageForecast::new)
                        .sort(Comparator.comparing(AverageForecast::getName))
                .collectList()
                .map(averages ->
                        {   writeCsvFile(averages);
                            return averages;
                        }
                );
    }

    private void writeCsvFile(List<AverageForecast> averages) {
        try {
            String filename = "response.csv";
            FileWriter fileWriter = new FileWriter(filename, false);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("Name, temperature, wind\n");

            for(AverageForecast average : averages) {
                bw.write(average.toCsvRow());
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("\nError writing to response.csv!");
            e.printStackTrace();
        }
    }

    private Mono<TempAndWindCityData> handleRemoteApiCall(String cityName) {
      return  WebClient.create(toPath(cityName))
                .get()
                .exchangeToMono(response -> {

                    if(response.statusCode().is2xxSuccessful()){
                        return response
                                .bodyToMono(TempAndWindCityData.class)
                                .map(tempAndWindCityData ->
                                    {
                                        tempAndWindCityData.setName(cityName);
                                        return tempAndWindCityData;
                                    }
                                );

                    }
                    else
                        return Mono.just(new TempAndWindCityData(cityName));
                });

    }

    private String toPath(String cityName) {
        return "https://998d8129-2264-4a98-a92e-ba8bde4a4d1c.mock.pstmn.io/" + cityName;
    }
}
