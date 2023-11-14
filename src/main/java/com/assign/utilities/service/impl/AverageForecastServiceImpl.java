package com.assign.utilities.service.impl;

import com.assign.utilities.pojo.AverageForecast;
import com.assign.utilities.pojo.Forecast;
import com.assign.utilities.pojo.TempAndWindData;
import com.assign.utilities.service.AverageForecastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AverageForecastServiceImpl implements AverageForecastService {
    @Override
    public AverageForecast computeAverageForecast(TempAndWindData data) {
        int tempSum = 0, tempCount = 0, windSum = 0, windCount = 0;

        for (Forecast f : data.getForecast()) {

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

        AverageForecast averageForecast = AverageForecast.builder()
                .cityName(data.getCityName())
                .temperature(temperature)
                .wind(wind)
                .build();

        log.info("Calculate average forecast: {}", averageForecast);
        return averageForecast;
    }
}
