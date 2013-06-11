package com.utterlyidle.metrics.core;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Pair;
import com.googlecode.utterlyidle.Status;
import com.utterlyidle.metrics.MetricsModule;

import java.util.HashMap;
import java.util.Map;

import static com.codahale.metrics.MetricRegistry.name;
import static com.googlecode.totallylazy.Sequences.sequence;

public class StatusCodeMeters {
    private Map<Integer, Meter> statusCodeMeters = new HashMap<Integer, Meter>();
    private MetricRegistry metricRegistry;
    private Meter otherMeter;

    public StatusCodeMeters(MetricRegistry metricRegistry, StatusCodes codes) {
        this.metricRegistry = metricRegistry;
        sequence(codes.codes()).map(toStatusCodeAndMeterPair()).fold(statusCodeMeters, StatusCodeMeters.<Integer, Meter>pairIntoMap());
        this.otherMeter = createMeter("other");
    }

    private static <A, B> Callable2<Map<? super A, ? super B>, Pair<? extends A, ? extends B>, Map<? super A, ? super B>> pairIntoMap() {
        return new Callable2<Map<? super A, ? super B>, Pair<? extends A, ? extends B>, Map<? super A, ? super B>>() {
            @Override
            public Map<? super A, ? super B> call(Map<? super A, ? super B> map, Pair<? extends A, ? extends B> pair) throws Exception {
                map.put(pair.first(), pair.second());
                return map;
            }
        };
    }

    private Callable1<Status, Pair<Integer, Meter>> toStatusCodeAndMeterPair() {
        return new Callable1<Status, Pair<Integer, Meter>>() {
            @Override
            public Pair<Integer, Meter> call(Status status) throws Exception {
                return Pair.pair(status.code(), createMeter(String.valueOf(status.code())));
            }
        };

    }

    private Meter createMeter(String s) {
        return metricRegistry.meter(name(MetricsModule.class, s));
    }

    public Meter get(int statusCode) {
        Meter meter = statusCodeMeters.get(statusCode);
        return meter != null ? meter : otherMeter;
    }
}
