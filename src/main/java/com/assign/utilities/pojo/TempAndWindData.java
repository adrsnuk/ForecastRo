package com.assign.utilities.pojo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TempAndWindData {

    private String cityName;
    private String temperature;
    private String wind;
    private String description;
    private List<Forecast> forecast;

    public TempAndWindData(String cityName) {
        this.cityName = cityName;
        this.temperature = "";
        this.wind = "";
        this.description = "";
        this.forecast = new ArrayList<>();
    }
}
