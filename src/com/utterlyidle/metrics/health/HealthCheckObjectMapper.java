package com.utterlyidle.metrics.health;

import com.codahale.metrics.json.HealthCheckModule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HealthCheckObjectMapper {
    private ObjectMapper objectMapper;

    public HealthCheckObjectMapper() {
        this.objectMapper = new ObjectMapper().registerModule(new HealthCheckModule());
    }

    public ObjectMapper mapper() {
        return objectMapper;
    }
}
