package com.assign.utilities.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Forecast {
    private String day;
    private String temperature;
    private String wind;
}
