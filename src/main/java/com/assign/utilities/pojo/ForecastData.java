package com.assign.utilities.pojo;

import lombok.*;

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

}
