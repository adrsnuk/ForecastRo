package com.assign.utilities.forecast.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.pojo.ForecastData;
import com.assign.utilities.pojo.ForecastDay;
import com.assign.utilities.service.impl.ForecastAverageCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ForecastAverageCalculatorServiceImplTest {

    private ForecastAverageCalculatorServiceImpl calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new ForecastAverageCalculatorServiceImpl();
    }

    @Test
    public void testComputeAverageForecast() {
        ForecastDay forecastDay1 = mock(ForecastDay.class);
        when(forecastDay1.getTemperature()).thenReturn("25");
        when(forecastDay1.getWind()).thenReturn("10");

        ForecastDay forecastDay2 = mock(ForecastDay.class);
        when(forecastDay2.getTemperature()).thenReturn("30");
        when(forecastDay2.getWind()).thenReturn("15");

        ForecastData forecastData = mock(ForecastData.class);
        when(forecastData.getForecast()).thenReturn(List.of(forecastDay1, forecastDay2));
        when(forecastData.getCityName()).thenReturn("Test City");

        ForecastAverage result = calculatorService.computeAverageForecast(forecastData);

        assertEquals("Test City", result.getCityName());
        assertEquals("27", result.getTemperature()); // (25 + 30) / 2 = 27
        assertEquals("12", result.getWind());       // (10 + 15) / 2 = 12
    }
}
