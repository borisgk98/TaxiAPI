#!/usr/bin/env bash
docker build java-back-server -t gcr.io/rich-sprite-242414/stax-java-back-server &&
docker push gcr.io/rich-sprite-242414/stax-java-back-server &&
docker build nodejs-front-server -t gcr.io/rich-sprite-242414/stax-java-back-server &&
docker push gcr.io/rich-sprite-242414/stax-java-back-server