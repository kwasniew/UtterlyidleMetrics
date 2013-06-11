package com.utterlyidle.metrics.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

public class MetricsObjectMapper {
    private ObjectMapper objectMapper;

    public MetricsObjectMapper() {
        this.objectMapper = new ObjectMapper().registerModule(new com.codahale.metrics.json.MetricsModule(TimeUnit.SECONDS, TimeUnit.SECONDS, true));
    }

    public ObjectMapper mapper() {
        return objectMapper;
    }
}
