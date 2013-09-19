package com.utterlyidle.metrics.core;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.utterlyidle.metrics.MetricsModule;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.codahale.metrics.MetricRegistry.name;

public class StatusCodeMeters {
    private ConcurrentMap<Integer, Meter> statusCodeMeters = new ConcurrentHashMap<Integer, Meter>();
    private MetricRegistry metricRegistry;

    public StatusCodeMeters(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    private Meter createMeter(String s) {
        return metricRegistry.meter(name(MetricsModule.class, s));
    }

    public Meter get(int statusCode) {
        Meter meter = statusCodeMeters.get(statusCode);
        if(meter == null) {
            meter = createMeter(String.valueOf(statusCode));
            statusCodeMeters.putIfAbsent(statusCode, meter);
        }
        return meter;
    }
}
