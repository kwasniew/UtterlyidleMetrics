package com.utterlyidle.metrics.core;

import com.googlecode.utterlyidle.Status;

public class StatusCodes {
    private Status[] codes;

    public StatusCodes(Status[] codes) {
        this.codes = codes;
    }

    public static StatusCodes codes(Status... codes) {
        return new StatusCodes(codes);
    }

    public Status[] codes() {
        return this.codes;
    }
}
