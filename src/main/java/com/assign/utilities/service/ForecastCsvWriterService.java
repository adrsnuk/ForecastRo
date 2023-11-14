package com.assign.utilities.service;

import com.assign.utilities.pojo.AverageForecast;

import java.util.List;

public interface ForecastCsvWriterService {

    List<AverageForecast> writeForecastToCsv(List<AverageForecast> averages);
}
