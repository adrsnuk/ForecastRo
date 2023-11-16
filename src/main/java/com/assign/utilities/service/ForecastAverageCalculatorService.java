package com.assign.utilities.service;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.pojo.ForecastData;

public interface ForecastAverageCalculatorService {

    ForecastAverage computeAverageForecast(ForecastData data);
}
