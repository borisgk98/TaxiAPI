#!/usr/bin/env bash
docker build java-back-server -t gcr.io/staxtaxi/stax-java-back-server &&
gcloud docker -- push gcr.io/staxtaxi/stax-java-back-server &&
docker build nodejs-front-server -t gcr.io/staxtaxi/stax-java-back-server &&
gcloud docker -- push gcr.io/staxtaxi/stax-java-back-server