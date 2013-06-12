package com.utterlyidle.metrics.core;

import com.codahale.metrics.Timer;
import com.googlecode.utterlyidle.HttpHandler;
import com.googlecode.utterlyidle.Request;
import com.googlecode.utterlyidle.Response;

public class MetricsHttpHandler implements HttpHandler {

    private HttpHandler handler;
    private RequestTimer requestTimer;
    private ActiveRequestsCounter activeRequestCounter;
    private StatusCodeMeters statusCodeMeters;

    public MetricsHttpHandler(HttpHandler handler, RequestTimer requestTimer, ActiveRequestsCounter activeRequestsCounter, StatusCodeMeters statusCodeMeters) {
        this.handler = handler;
        this.requestTimer = requestTimer;
        this.activeRequestCounter = activeRequestsCounter;
        this.statusCodeMeters = statusCodeMeters;
    }

    @Override
    public Response handle(Request request) throws Exception {
        activeRequestCounter.get().inc();
        Timer.Context timerContext = requestTimer.get().time();
        Response response = null;
        try {
            response = handler.handle(request);
            return response;
        } finally {
            timerContext.stop();
            activeRequestCounter.get().dec();
            if(response != null) {
                markMeterForStatusCode(response.status().code());
            }
        }
    }

    private void markMeterForStatusCode(int statusCode) {
        statusCodeMeters.get(statusCode).mark();
    }
}
