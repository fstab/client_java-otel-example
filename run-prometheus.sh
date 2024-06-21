#!/bin/bash

set -e

PROMETHEUS_VERSION=2.52.0

# Download Prometheus if it doesn't exist

if [[ ! -f prometheus-$PROMETHEUS_VERSION.linux-amd64.tar.gz ]] ; then
	echo Downloading prometheus-$PROMETHEUS_VERSION.linux-amd64.tar.gz
	curl -sOL https://github.com/prometheus/prometheus/releases/download/v$PROMETHEUS_VERSION/prometheus-$PROMETHEUS_VERSION.linux-amd64.tar.gz
fi

if [[ ! -d prometheus-$PROMETHEUS_VERSION.linux-amd64 ]] ; then
	echo Extracting prometheus-$PROMETHEUS_VERSION.linux-amd64.tar.gz
	tar xfz prometheus-$PROMETHEUS_VERSION.linux-amd64.tar.gz
fi

# The native-histograms and exemplar-storage are not required for jmx_exporter, because jmx_exporter does not
# use histograms and exemplars. However, for full OpenTelemetry support you need these feature flags.

./prometheus-$PROMETHEUS_VERSION.linux-amd64/prometheus \
	--config.file=prometheus.yml \
	--enable-feature=otlp-write-receiver \
	--enable-feature=native-histograms \
	--enable-feature=exemplar-storage
