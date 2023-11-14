package com.assign.utilities.service.impl;

import com.assign.utilities.pojo.AverageForecast;
import com.assign.utilities.service.ForecastCsvWriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ForecastCsvWriterServiceImpl implements ForecastCsvWriterService {

    private final static String FILENAME = "response.csv";

    @Override
    public List<AverageForecast> writeForecastToCsv(List<AverageForecast> averages) {
        try (FileWriter fileWriter = new FileWriter(FILENAME, false);
             BufferedWriter bw = new BufferedWriter(fileWriter)) {

            log.info("Reset {} file", FILENAME);
            bw.write("Name, temperature, wind\n");

            for (AverageForecast forecastAverage : averages) {
                String csvRow = toCsvRow(forecastAverage);
                log.info("Append to csv file: " + csvRow);
                bw.write(csvRow + "\n");
            }

            bw.flush();

        } catch (IOException e) {
            log.info("\nError writing to response.csv!");
            e.printStackTrace();
        }

        return averages;
    }

    private String toCsvRow(AverageForecast forecastAverage) {
        String cityName = forecastAverage.getCityName();
        String temperature = forecastAverage.getTemperature();
        String wind = forecastAverage.getWind();
        return String.format("%s,%s,%s", cityName, temperature, wind);
    }
}
