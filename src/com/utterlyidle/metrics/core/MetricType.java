package com.utterlyidle.metrics.core;

import com.codahale.metrics.Metric;

public class MetricType<T extends Metric> {
    private final T t;

    public MetricType(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    };
}
