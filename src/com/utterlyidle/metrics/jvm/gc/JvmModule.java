package com.utterlyidle.metrics.jvm.gc;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Maps;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.yadic.Container;

import java.lang.management.ManagementFactory;
import java.util.Map;

public class JvmModule implements ApplicationScopedModule {
    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        container.addInstance(GarbageCollectorMetricSet.class, new GarbageCollectorMetricSet(ManagementFactory.getGarbageCollectorMXBeans()));
        registerGarbageCollectorMetrics(container.get(MetricRegistry.class), container.get(GarbageCollectorMetricSet.class));
        return container;
    }

    private void registerGarbageCollectorMetrics(MetricRegistry metricRegistry, GarbageCollectorMetricSet garbageCollectorMetricSet) {
        Map<String, Metric> metrics = garbageCollectorMetricSet.getMetrics();
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
