package com.utterlyidle.metrics.ping;

import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

public class PingResource {

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response ping() {
        return ResponseBuilder.response(Status.OK).header(HttpHeaders.CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity("pong").build();
    }

}
