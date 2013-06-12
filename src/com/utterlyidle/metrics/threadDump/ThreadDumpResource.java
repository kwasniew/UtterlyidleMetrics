package com.utterlyidle.metrics.threadDump;

import com.codahale.metrics.jvm.ThreadDump;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Closeables;
import com.googlecode.totallylazy.Strings;
import com.googlecode.utterlyidle.*;
import com.googlecode.utterlyidle.annotations.GET;
import com.googlecode.utterlyidle.annotations.Path;
import com.googlecode.utterlyidle.annotations.Produces;

import java.io.ByteArrayOutputStream;

@Path("metrics")
public class ThreadDumpResource {
    public static final String NAME = "threads";
    private ThreadDump threadDump;

    public ThreadDumpResource(ThreadDump threadDump) {
        this.threadDump = threadDump;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path(NAME)
    public Response threadDump() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        threadDump.dump(outputStream);
        String entityBody = Closeables.using(outputStream, byteArrayOutputStreamToString());
        return ResponseBuilder.response(Status.OK).header(HttpHeaders.CACHE_CONTROL, "must-revalidate,no-cache,no-store").entity(entityBody).build();
    }

    private Callable1<ByteArrayOutputStream, String> byteArrayOutputStreamToString() {
        return new Callable1<ByteArrayOutputStream, String>() {
            @Override
            public String call(ByteArrayOutputStream byteArrayOutputStream) throws Exception {
                return Strings.string(byteArrayOutputStream.toByteArray());
            }
        };
    }

}
