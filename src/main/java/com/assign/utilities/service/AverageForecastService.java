package com.assign.utilities.service;

import com.assign.utilities.pojo.AverageForecast;
import com.assign.utilities.pojo.Forecast;
import com.assign.utilities.pojo.TempAndWindData;

public interface AverageForecastService {

    AverageForecast computeAverageForecast(TempAndWindData data);
}
