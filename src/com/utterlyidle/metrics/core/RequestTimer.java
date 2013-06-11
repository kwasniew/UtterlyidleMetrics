package com.utterlyidle.metrics.core;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.utterlyidle.metrics.MetricsModule;

import static com.codahale.metrics.MetricRegistry.name;

public class RequestTimer extends MetricType<Timer> {
    public RequestTimer(MetricRegistry metricRegistry) {
        super(metricRegistry.timer(name(MetricsModule.class, "requestsTimer")));
    }

}
