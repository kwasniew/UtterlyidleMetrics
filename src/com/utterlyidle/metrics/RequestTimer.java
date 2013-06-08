package com.utterlyidle.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import static com.codahale.metrics.MetricRegistry.name;

public class RequestTimer extends MetricType<Timer> {
    public RequestTimer(MetricRegistry metricRegistry) {
        super(metricRegistry.timer(name(MetricsModule.class, "requestsTimer")));
    }

}
