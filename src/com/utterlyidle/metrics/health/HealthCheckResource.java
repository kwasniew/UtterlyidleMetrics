package com.utterlyidle.metrics.health;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.totallylazy.Predicate;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.Status;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import java.util.SortedMap;

import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.predicates.Not.not;
import static com.googlecode.utterlyidle.HttpHeaders.CACHE_CONTROL;
import static com.googlecode.utterlyidle.MediaType.APPLICATION_JSON;
import static com.googlecode.utterlyidle.Status.*;

public class HealthCheckResource {

    private ObjectMapper objectMapper;
    private HealthCheckRegistry healthCheckRegistry;

    public HealthCheckResource(HealthCheckRegistry healthCheckRegistry, HealthCheckObjectMapper healthCheckObjectMapper) {
        this.healthCheckRegistry = healthCheckRegistry;
        this.objectMapper = healthCheckObjectMapper.mapper();
    }

    @GET
    @Path("healthcheck")
    @Produces(APPLICATION_JSON)
    public Response healthCheck() throws JsonProcessingException {
        SortedMap<String,HealthCheck.Result> results = healthCheckRegistry.runHealthChecks();

        String healthCheckResults = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(results);

        return ResponseBuilder.response(statusCode(results)).header(CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(healthCheckResults).build();
    }

    private Status statusCode(SortedMap<String, HealthCheck.Result> results) {
        if(results.isEmpty())  {
            return NOT_IMPLEMENTED;
        } else {
            if(isAllHealthy(results)) {
                return OK;
            } else {
                return INTERNAL_SERVER_ERROR;
            }
        }
    }

    private boolean isAllHealthy(SortedMap<String, HealthCheck.Result> results) {
        if(sequence(results.values()).find(not(healthy())).isEmpty()) {
            return true;
        }
        return false;
    }

    private Predicate<? super HealthCheck.Result> healthy() {
        return new Predicate<HealthCheck.Result>() {
            @Override
            public boolean matches(HealthCheck.Result result) {
                return result.isHealthy();
            }
        };
    }

}
