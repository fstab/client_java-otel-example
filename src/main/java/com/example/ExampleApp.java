package com.example;

import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.exporter.opentelemetry.OpenTelemetryExporter;
import io.prometheus.metrics.instrumentation.jvm.JvmMemoryMetrics;

import java.io.IOException;

public class ExampleApp {

    public static void main( String[] args ) throws IOException, InterruptedException {

        // Enable JVM metrics so we have some example metrics.
        JvmMemoryMetrics.builder().register();

        // We expose metrics in Prometheus text format in addition to pushing them via OpenTelemetry
        // so that you can quickly check which metrics are produced.
        // The Prometheus server is NOT configured to scrape these metrics.
        HTTPServer.builder()
                .port(9000)
                .buildAndStart();
        System.out.println("Exporting Prometheus metrics on http://localhost:9000/metrics");

        // The following pushes the metrics in OpenTelemetry format to the Prometheus server's OTLP endpoint.
        OpenTelemetryExporter.builder()
                // the default protocol in OpenTelemetry is actually "grpc", but I think the Prometheus server only supports "http/protobuf"
                .protocol("http/protobuf")
                // the prometheus server opens this endpoint when it's started with the enable-feature=otlp-write-receiver feature flag
                .endpoint("http://localhost:9090/api/v1/otlp")
                // the default push interval in OpenTelemetry is 60 seconds. for a quick test, it's convenient to have a much shorter interval
                .intervalSeconds(5)
                .buildAndStart();
        System.out.println("Pushing OpenTelemetry metrics to http://localhost:9090/api/v1/otlp");

        Thread.currentThread().join(); // wait forever
    }
}
