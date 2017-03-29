#!/usr/bin/env bash
rm -rf *.jar
cp -R   micro_docker/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/docker_consul:v0.0.1 .
docker push 192.168.200.23:5002/micro-spring-cloud/docker_consul:v0.0.1
