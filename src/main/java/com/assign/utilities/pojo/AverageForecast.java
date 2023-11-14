package com.assign.utilities.pojo;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AverageForecast {

    private String cityName;
    private String temperature;
    private String wind;
}
