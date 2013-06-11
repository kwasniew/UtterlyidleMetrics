package com.example;

import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;

public class MainResource {
    @GET
    @Path("/metricsTest")
    public String hello() {
        return "<h2>UtterlyidleMetrics</h2>";
    }
}
