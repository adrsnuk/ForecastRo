package com.assign.utilities.service.impl;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.pojo.ForecastDay;
import com.assign.utilities.pojo.ForecastData;
import com.assign.utilities.service.ForecastAverageCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ForecastAverageCalculatorServiceImpl implements ForecastAverageCalculatorService {
    @Override
    public ForecastAverage computeAverageForecast(ForecastData forecastData) {
        int tempSum = 0, tempCount = 0, windSum = 0, windCount = 0;

        for (ForecastDay f : forecastData.getForecast()) {

            if (!f.getTemperature().isBlank()) {
                tempSum += Integer.parseInt(f.getTemperature());
                tempCount++;
            }

            if (!f.getWind().isBlank()) {
                windSum += Integer.parseInt(f.getWind());
                windCount++;
            }
        }

        String temperature = tempCount == 0 ? "" : String.valueOf(tempSum / tempCount);
        String wind = windCount == 0 ? "" : String.valueOf(windSum / windCount);

        ForecastAverage averageForecast = ForecastAverage.builder()
                .cityName(forecastData.getCityName())
                .temperature(temperature)
                .wind(wind)
                .build();

        log.info("Calculate average forecast: {}", averageForecast);
        return averageForecast;
    }
}
