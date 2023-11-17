package com.assign.utilities.response.parts;

import com.assign.utilities.pojo.ForecastAverage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;

import java.util.List;

public class ForecastJsonPart implements Part {
    private final String jsonContent;
    private final HttpHeaders headers;

    public ForecastJsonPart(List<ForecastAverage> forecastAverages, ObjectMapper objectMapper) {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            this.jsonContent = objectMapper.writeValueAsString(forecastAverages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON content", e);
        }
    }

    @Override
    public String name() {
        return "JSON Section";
    }

    @Override
    public Flux<DataBuffer> content() {
        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();
        byte[] contentBytes = jsonContent.getBytes();

        return Flux.just(bufferFactory.wrap(contentBytes));
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }
}
