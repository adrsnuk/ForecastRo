package com.assign.utilities.service;

import com.assign.utilities.pojo.ForecastAverage;

import java.util.List;

public interface ForecastCsvWriterService {

    List<ForecastAverage> writeForecastToCsv(List<ForecastAverage> averages);
}
