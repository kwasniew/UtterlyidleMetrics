package com.utterlyidle.metrics;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.googlecode.utterlyidle.MediaType.APPLICATION_JSON;

public class MetricsResource {

    private MetricRegistry metricRegistry;
    private ObjectMapper objectMapper;

    public MetricsResource(MetricRegistry metricRegistry, ObjectMapper objectMapper) {
        this.metricRegistry = metricRegistry;
        this.objectMapper = objectMapper;
    }

    @GET
    @Path("metrics")
    @Produces(APPLICATION_JSON)
    public Response metrics() throws Exception {
        String metrics = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metricRegistry);
        return ResponseBuilder.response(Status.OK).header(HttpHeaders.CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(metrics).build();
    }
}
