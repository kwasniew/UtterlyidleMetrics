package com.utterlyidle.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import static com.codahale.metrics.MetricRegistry.name;

public class ActiveRequestsCounter extends MetricType<Counter> {
    public ActiveRequestsCounter(MetricRegistry metricRegistry) {
        super(metricRegistry.counter(name(MetricsModule.class, "activeRequestsCounter")));
    }
}
