#!/usr/bin/env bash
kubectl delete -f ./ops/k8s/default/stax-java-back-server.yaml
kubectl delete -f ./ops/k8s/default/stax-nodejs-front-server.yaml
kubectl apply -f ./ops/k8s/default/stax-java-back-server.yaml
kubectl apply -f ./ops/k8s/default/stax-nodejs-front-server.yaml
kubectl get service | grep LoadBalancer | grep nodejs | awk '{ print $4":3000" }'
