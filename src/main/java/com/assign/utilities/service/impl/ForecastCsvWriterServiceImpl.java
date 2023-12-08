package com.assign.utilities.service.impl;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.service.ForecastCsvWriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ForecastCsvWriterServiceImpl implements ForecastCsvWriterService {

    private final static String FILENAME = "response.csv";

    public Mono<List<ForecastAverage>> writeAsync(List<ForecastAverage> averages) {
        return Mono.just(averages)
                .doAfterTerminate(() -> writeForecastToCsv(averages));
    }

    public void writeForecastToCsv(List<ForecastAverage> averages) {
        Mono.fromRunnable(() -> {

            try (FileWriter fileWriter = new FileWriter(FILENAME, false);
                 BufferedWriter bw = new BufferedWriter(fileWriter)) {

                log.info("Reset {} file", FILENAME);
                bw.write("Name, temperature, wind\n");

                for (ForecastAverage forecastAverage : averages) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    String csvRow = toCsvRow(forecastAverage);
                    log.info("Append to csv file: " + csvRow);
                    bw.write(csvRow + "\n");
                }

                bw.flush();

            } catch (IOException e) {
                log.info("\nError writing to response.csv!");
                e.printStackTrace();
            }

        }).subscribe();
    }

    private String toCsvRow(ForecastAverage forecastAverage) {
        String cityName = forecastAverage.getCityName();
        String temperature = forecastAverage.getTemperature();
        String wind = forecastAverage.getWind();
        return String.format("%s,%s,%s", cityName, temperature, wind);
    }
}
