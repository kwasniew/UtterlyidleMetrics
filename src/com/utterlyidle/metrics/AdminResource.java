package com.utterlyidle.metrics;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;
import com.utterlyidle.metrics.core.MetricsResource;
import com.utterlyidle.metrics.health.HealthCheckResource;
import com.utterlyidle.metrics.ping.PingResource;
import com.utterlyidle.metrics.threadDump.ThreadDumpResource;

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
    private BasePath basePath;


    public AdminResource(BasePath basePath) {
        this.basePath = basePath;
    }

    @GET
    @Path("admin")
    @Produces(TEXT_HTML)
    public Response admin() throws Exception {
        String content = TEMPLATE.replace("${METRICS}", uri(MetricsResource.NAME)).
                                  replace("${PING}", uri(PingResource.NAME)).
                                  replace("${THREADS}", uri(ThreadDumpResource.NAME)).
                                  replace("${HEALTHCHECK}", uri(HealthCheckResource.NAME));

        return ResponseBuilder.response(OK).header(CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(content).build();
    }

    private String uri(String lastSegment) {
        return String.format("%s%s/%s", basePath.toString(), "metrics", lastSegment);
    }
}
