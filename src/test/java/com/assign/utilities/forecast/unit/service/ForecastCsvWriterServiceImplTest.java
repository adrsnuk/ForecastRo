package com.assign.utilities.forecast.unit.service;

import com.assign.utilities.pojo.ForecastAverage;
import com.assign.utilities.service.impl.ForecastCsvWriterServiceImpl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ForecastCsvWriterServiceImplTest {

    @Test
    public void testWriteAsync() throws Exception {
        ForecastCsvWriterServiceImpl csvWriterService = new ForecastCsvWriterServiceImpl();

        ForecastCsvWriterServiceImpl spyCsvWriterService = spy(csvWriterService);
        doNothing().when(spyCsvWriterService).writeForecastToCsv(anyList());

        ForecastAverage forecast1 = ForecastAverage.builder()
                .cityName("Arad")
                .temperature("")
                .wind("")
                .build();

        ForecastAverage forecast2 = ForecastAverage.builder()
                .cityName("Baia-Mare")
                .temperature("")
                .wind("")
                .build();

        ForecastAverage forecast3 = ForecastAverage.builder()
                .cityName("Cluj-Napoca")
                .temperature("")
                .wind("")
                .build();

        ForecastAverage forecast4 = ForecastAverage.builder()
                .cityName("Constanta")
                .temperature("")
                .wind("")
                .build();

        ForecastAverage forecast5 = ForecastAverage.builder()
                .cityName("Timisoara")
                .temperature("")
                .wind("")
                .build();

        List<ForecastAverage> averages = Arrays.asList(forecast1, forecast2, forecast3, forecast4, forecast5);

        Mono<List<ForecastAverage>> resultMono = spyCsvWriterService.writeAsync(averages);

        StepVerifier.create(resultMono)
                .expectNext(averages)
                .verifyComplete();

        verify(spyCsvWriterService, times(1)).writeForecastToCsv(eq(averages));

        Path filePath = Paths.get("response.csv");
        List<String> lines = Files.readAllLines(filePath);
        assertAll(
                () -> assertTrue(lines.get(0).startsWith("Name, temperature, wind")),
                () -> assertTrue(lines.get(1).startsWith("Arad,,")),
                () -> assertTrue(lines.get(2).startsWith("Baia-Mare,,")),
                () -> assertTrue(lines.get(3).startsWith("Cluj-Napoca,,")),
                () -> assertTrue(lines.get(4).startsWith("Constanta,,")),
                () -> assertTrue(lines.get(5).startsWith("Timisoara,,"))
        );

        Files.deleteIfExists(filePath);
    }
}