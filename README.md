utterlyidle-metrics
================

Utterlyidle module that adds plug and play metrics (https://github.com/codahale/metrics) support. 
In particular it ports servlet related functionality (metric-servlet and metric-servlets modules) to Utterlyidle.

Getting started
--------

Prerequisites: Java 1.6, ant

1.  git clone _repo_url_
2.  cd utterlyidle-metrics
3.  ant (builds the app)
4.  run main method from com.example.Main
5.  Go to http://localhost:8181/metrics/admin


Building the project
--------

Prerequisites: Java 1.6, ant

`ant -Dbuild.number=100 build`


Usage
--------

Add utterlyidle-metrics jar and metrics runtime dependencies

`application.add(new MetricsModule());`

Default metrics
--------
/metrics/admin -- aggregate of all metrics, healthchecks etc.  
/metrics/ping   
/metrics/threads  -- thread dump  
/metrics/healthcheck -- deadlock detection healthcheck; other custom healthchecks will be added here  
/metrics/metrics -- core metrics: active requests counter, response code meters, requests timer  


