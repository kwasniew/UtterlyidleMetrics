package com.utterlyidle.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.codahale.metrics.jvm.ThreadDump;
import com.googlecode.utterlyidle.HttpHandler;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.utterlyidle.modules.RequestScopedModule;
import com.googlecode.utterlyidle.modules.ResourcesModule;
import com.googlecode.yadic.Container;
import com.utterlyidle.metrics.core.*;
import com.utterlyidle.metrics.health.HealthCheckObjectMapper;
import com.utterlyidle.metrics.health.HealthCheckResource;
import com.utterlyidle.metrics.ping.PingResource;
import com.utterlyidle.metrics.threadDump.ThreadDumpResource;

import java.lang.management.ManagementFactory;

import static com.googlecode.utterlyidle.Status.*;
import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

public class MetricsModule implements ApplicationScopedModule, RequestScopedModule, ResourcesModule
{
    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        container.add(MetricRegistry.class);
        container.add(RequestTimer.class);
        container.add(ActiveRequestsCounter.class);
        container.addInstance(StatusCodes.class, StatusCodes.codes(OK, CREATED, NO_CONTENT, SEE_OTHER, NOT_MODIFIED, NOT_FOUND, BAD_REQUEST, INTERNAL_SERVER_ERROR, BAD_GATEWAY));
        container.add(StatusCodeMeters.class);
        container.add(MetricsObjectMapper.class);

        container.addInstance(ThreadDump.class, new ThreadDump(ManagementFactory.getThreadMXBean()));

        container.add(HealthCheckRegistry.class);
        container.add(HealthCheckObjectMapper.class);
        container.get(HealthCheckRegistry.class).register("threadDeadlock", new ThreadDeadlockHealthCheck());

        return container;
    }

    @Override
    public Container addPerRequestObjects(Container container) throws Exception {
        container.decorate(HttpHandler.class, MetricsHttpHandler.class);
        return container;
    }

    @Override
    public Resources addResources(Resources bindings) throws Exception {
        bindings.add(annotatedClass(PingResource.class));
        bindings.add(annotatedClass(ThreadDumpResource.class));
        bindings.add(annotatedClass(MetricsResource.class));
        bindings.add(annotatedClass(HealthCheckResource.class));
        bindings.add(annotatedClass(AdminResource.class));
        return bindings;
    }
}
