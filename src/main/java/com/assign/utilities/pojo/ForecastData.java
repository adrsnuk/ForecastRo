package com.assign.utilities.pojo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForecastData {

    private String cityName;
    private String temperature;
    private String wind;
    private String description;
    private List<ForecastDay> forecast;

    public ForecastData(String cityName) {
        this.cityName = cityName;
        this.temperature = "";
        this.wind = "";
        this.description = "";
        this.forecast = new ArrayList<>();
    }
}
