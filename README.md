UtterlyidleMetrics
================

Utterlyidle module that adds metrics (https://github.com/codahale/metrics) support. 
In particular it ports servlet related functionality (metric-servlet and metric-servlets modules) to Utterlyidle.

Getting started
--------

Prerequisites: Java 1.6, ant

1.  git clone --recursive _repo_url_
2.  cd UtterlyidleSetup
3.  ant (builds the app)
4.  ant run (runs the app using a default port of 8181)
5.  Go to http://localhost:8181/


Building the project
--------

Prerequisites: Java 1.6, ant

`ant -Dbuild.number=100 package`


Usage
--------

Add UtterlyidleMetrics jar and metrics runtime dependencies

`application.add(new MetricsModule());`


