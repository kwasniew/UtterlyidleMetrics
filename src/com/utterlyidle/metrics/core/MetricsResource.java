package com.utterlyidle.metrics.core;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import static com.googlecode.utterlyidle.HttpHeaders.CACHE_CONTROL;
import static com.googlecode.utterlyidle.MediaType.APPLICATION_JSON;
import static com.googlecode.utterlyidle.Status.OK;

@Path("metrics")
public class MetricsResource {
    public static final String NAME = "metrics";
    private MetricRegistry metricRegistry;
    private ObjectMapper objectMapper;

    public MetricsResource(MetricRegistry metricRegistry, MetricsObjectMapper metricsObjectMapper) {
        this.metricRegistry = metricRegistry;
        this.objectMapper = metricsObjectMapper.mapper();
    }

    @GET
    @Path(NAME)
    @Produces(APPLICATION_JSON)
    public Response metrics() throws Exception {
        String metrics = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metricRegistry);
        return ResponseBuilder.response(OK).header(CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(metrics).build();
    }
}
