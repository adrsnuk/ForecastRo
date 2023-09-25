package com.assign.utilities;

import com.assign.utilities.dto.Forecast;
import com.assign.utilities.dto.TempAndWindCityData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageForecast {
    private String name;
    private String temperature;
    private String wind;

    public AverageForecast(TempAndWindCityData twd){
        this.name = twd.getName();

        int tempSum = 0, tempCount = 0, windSum = 0, windCount = 0;

        for(Forecast f: twd.getForecast()){

            if(!f.getTemperature().isBlank()){
                tempSum += Integer.parseInt(f.getTemperature());
                tempCount++;
            }

            if(!f.getWind().isBlank()){
                windSum += Integer.parseInt(f.getWind());
                windCount++;
            }
        }

        this.temperature = tempCount == 0 ? "": String.valueOf(tempSum / tempCount);
        this.wind = windCount == 0 ? "" : String.valueOf(windSum / windCount);
    }

    public String toCsvRow(){
        return this.name + "," + this.getTemperature() + "," + this.getWind() + "\n";
    }
}
