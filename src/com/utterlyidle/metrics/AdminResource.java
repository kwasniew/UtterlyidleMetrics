package com.utterlyidle.metrics;

import com.googlecode.utterlyidle.Response;
import com.googlecode.utterlyidle.ResponseBuilder;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import static com.googlecode.utterlyidle.HttpHeaders.CACHE_CONTROL;
import static com.googlecode.utterlyidle.MediaType.TEXT_HTML;
import static com.googlecode.utterlyidle.Status.OK;

@Path("metrics")
public class AdminResource {
    // TODO: parametrize URLs with reflection based resource lookups
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
                    "    <li><a href=\"metrics\">Metrics</a></li>%n" +
                    "    <li><a href=\"ping\">Ping</a></li>%n" +
                    "    <li><a href=\"threads\">Threads</a></li>%n" +
                    "    <li><a href=\"healthcheck\">Healthcheck</a></li>%n" +
                    "  </ul>%n" +
                    "</body>%n" +
                    "</html>");


    @GET
    @Path("admin")
    @Produces(TEXT_HTML)
    public Response admin() throws Exception {
        return ResponseBuilder.response(OK).header(CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(TEMPLATE).build();
    }
}
