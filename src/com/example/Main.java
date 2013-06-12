package com.example;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyidle.metrics.MetricsModule;
import com.utterlyidle.metrics.jvm.JvmModule;

import static com.googlecode.utterlyidle.ApplicationBuilder.application;
import static com.googlecode.utterlyidle.ServerConfiguration.defaultConfiguration;

public class Main extends RestApplication {
    public Main(BasePath basePath) {
        super(basePath);
        add(new MetricsModule());
    }

    public static void main(String[] args) {
        int port = 8181;
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        application().add(new MetricsModule()).add(new JvmModule()).start(defaultConfiguration().port(port));
    }
}