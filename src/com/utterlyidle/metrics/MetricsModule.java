package com.utterlyidle.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.ThreadDump;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.utterlyidle.HttpHandler;
import com.googlecode.utterlyidle.Resources;
import com.googlecode.utterlyidle.modules.ApplicationScopedModule;
import com.googlecode.utterlyidle.modules.RequestScopedModule;
import com.googlecode.utterlyidle.modules.ResourcesModule;
import com.googlecode.yadic.Container;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import static com.googlecode.utterlyidle.Status.*;
import static com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass;

/**
 * This is the Utterlyidle port of: https://github.com/codahale/metrics/tree/master/metrics-servlet
 */

public class MetricsModule implements ApplicationScopedModule, RequestScopedModule, ResourcesModule
{
    @Override
    public Container addPerApplicationObjects(Container container) throws Exception {
        container.add(MetricRegistry.class);
        container.add(RequestTimer.class);
        container.add(ActiveRequestsCounter.class);
        container.addInstance(StatusCodes.class, StatusCodes.codes(OK, CREATED, NO_CONTENT, SEE_OTHER, NOT_FOUND, BAD_REQUEST, INTERNAL_SERVER_ERROR));
        container.add(StatusCodeMeters.class);

        container.addInstance(ThreadDump.class, new ThreadDump(ManagementFactory.getThreadMXBean()));
        container.addInstance(ObjectMapper.class, new ObjectMapper().registerModule(new com.codahale.metrics.json.MetricsModule(TimeUnit.SECONDS, TimeUnit.SECONDS, true)));


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
        return bindings;
    }
}
