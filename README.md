Example of the [Prometheus Java client library's](https://github.com/prometheus/client_java) support for [pushing metrics in OpenTelemetry format](https://prometheus.github.io/client_java/otel/otlp/).

This repository is a simple example that shows how to push Prometheus metrics to a Prometheus server using OTLP.

Clone this repo:

```
git clone https://github.com/fstab/client_java-otel-example.git
cd ./client_java-otel-example/
```

Download and run the Prometheus server with the OTLP endpoint enabled:

```
./run-prometheus.sh
```

Compile and run the Java example:

```
./mvnw package
java -jar target/client_java-otel-example.jar
```

The example program exposes metrics two times
* It exposed metrics in Promethus text format on [http://localhost:9000/metrics](http://localhost:9000/metrics)
* It pushes metrics to the Prometheus server's OpenTelemetry endpoint [http://localhost:9090/api/v1/otlp](http://localhost:9090/api/v1/otlp)

The Prometheus server is _not_ configured to scrape the Prometheus endpoint. The Prometheus endpoint is there so you can quickly check with your browser which metrics should be available. Access [http://localhost:9000/metrics](http://localhost:9000/metrics) to see which metrics should be available, and then run a few PromQL queries on [http://localhost:9090](http://localhost:9090) to see if these metrics have actually been pushed via OTLP.
