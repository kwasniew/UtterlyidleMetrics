package com.utterlyidle.metrics;

import com.codahale.metrics.jvm.ThreadDump;
import com.googlecode.totallylazy.Strings;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import java.io.ByteArrayOutputStream;

public class ThreadDumpResource {

    private ThreadDump threadDump;

    public ThreadDumpResource(ThreadDump threadDump) {
        this.threadDump = threadDump;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("threadDump")
    public Response threadDump() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        threadDump.dump(outputStream);
        String entityBody = Strings.string(outputStream.toByteArray());
        return ResponseBuilder.response(Status.OK).header(HttpHeaders.CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(entityBody).build();
    }

}
