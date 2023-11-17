package com.assign.utilities.response.parts;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ForecastCsvPart implements Part {

    public static final String SOURCE_FILE_NAME = "response.csv";

    @Override
    public String name() {
        return "Csv Part";
    }

    @Override
    public Flux<DataBuffer> content() {
        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();

        Path csvFilePath = Paths.get(SOURCE_FILE_NAME);

        try {
            byte[] contentBytes = Files.readAllBytes(csvFilePath);
            DataBuffer dataBuffer = bufferFactory.wrap(contentBytes);

            return Flux.just(dataBuffer);
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    @Override
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "data.csv");
        return headers;
    }
}

