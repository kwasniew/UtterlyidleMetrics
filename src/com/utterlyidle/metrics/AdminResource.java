package com.utterlyidle.metrics;

import com.googlecode.utterlyidle.Redirector;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;
import com.utterlyidle.metrics.core.MetricsResource;
import com.utterlyidle.metrics.health.HealthCheckResource;
import com.utterlyidle.metrics.ping.PingResource;
import com.utterlyidle.metrics.threadDump.ThreadDumpResource;

import static com.googlecode.totallylazy.proxy.Call.method;
import static com.googlecode.totallylazy.proxy.Call.on;
import static com.googlecode.utterlyidle.HttpHeaders.CACHE_CONTROL;
import static com.googlecode.utterlyidle.MediaType.TEXT_HTML;
import static com.googlecode.utterlyidle.Status.OK;

@Path("metrics")
public class AdminResource {
    private static final String TEMPLATE = String.format(
            "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"%n" +
                    "        \"http://www.w3.org/TR/html4/loose.dtd\">%n" +
                    "<html>%n" +
                    "<head>%n" +
                    "  <title>Metrics</title>%n" +
                    "</head>%n" +
                    "<body>%n" +
                    "  <h1>Operational Menu</h1>%n" +
                    "  <ul>%n" +
                    "    <li><a href=\"${METRICS}\">Metrics</a></li>%n" +
                    "    <li><a href=\"${PING}\">Ping</a></li>%n" +
                    "    <li><a href=\"${THREADS}\">Threads</a></li>%n" +
                    "    <li><a href=\"${HEALTHCHECK}\">Healthcheck</a></li>%n" +
                    "  </ul>%n" +
                    "</body>%n" +
                    "</html>");
    private Redirector redirector;


    public AdminResource(Redirector redirector) {
        this.redirector = redirector;
    }

    @GET
    @Path("admin")
    @Produces(TEXT_HTML)
    public Response admin() throws Exception {
        String metricsUri = redirector.absoluteUriOf(method(on(MetricsResource.class).metrics())).toString();
        String pingUri = redirector.absoluteUriOf(method(on(PingResource.class).ping())).toString();
        String threadsUri = redirector.absoluteUriOf(method(on(ThreadDumpResource.class).threadDump())).toString();
        String healthCheckUri = redirector.absoluteUriOf(method(on(HealthCheckResource.class).healthCheck())).toString();

        String content = TEMPLATE.replace("${METRICS}", metricsUri).
                                  replace("${PING}", pingUri).
                                  replace("${THREADS}", threadsUri).
                                  replace("${HEALTHCHECK}", healthCheckUri);

        return ResponseBuilder.response(OK).header(CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(content).build();
    }
}
