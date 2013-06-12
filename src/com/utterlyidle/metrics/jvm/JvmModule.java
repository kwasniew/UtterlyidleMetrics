package com.utterlyidle.metrics.jvm;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Maps;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.yadic.Container;

import java.util.Map;

public class JvmModule implements ApplicationScopedModule {
    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        container.add(GarbageCollectorMetricSet.class);
        container.add(MemoryUsageGaugeSet.class);
        registerMetrics(container.get(MetricRegistry.class), container.get(GarbageCollectorMetricSet.class));
        registerMetrics(container.get(MetricRegistry.class), container.get(MemoryUsageGaugeSet.class));
        return container;
    }

    private void registerMetrics(MetricRegistry metricRegistry, MetricSet metricSet) {
        Map<String, Metric> metrics = metricSet.getMetrics();
        Maps.entries(metrics).fold(metricRegistry, registerMetric());
    }

    private Callable2<MetricRegistry, Map.Entry<String, Metric>, MetricRegistry> registerMetric() {
        return new Callable2<MetricRegistry, Map.Entry<String, Metric>, MetricRegistry>() {
            @Override
            public MetricRegistry call(MetricRegistry metricRegistry, Map.Entry<String, Metric> entry) throws Exception {
                metricRegistry.register(entry.getKey(), entry.getValue());
                return metricRegistry;
            }
        };
    }
}
