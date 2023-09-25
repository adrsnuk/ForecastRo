package com.assign.utilities.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class TempAndWindCityData {

    private String name;
    private String temperature;
    private String wind;
    private String description;
    private List<Forecast> forecast;

    public TempAndWindCityData(String cityName) {
        this.name = cityName;
        this.temperature = "";
        this.wind = "";
        this.description = "";
        this.forecast = new ArrayList<>();
    }
}
