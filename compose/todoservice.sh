#!/usr/bin/env bash
cd ~/micro_springcloud/
rm -rf *.jar
#mvn clean
#mvn package
cp -R micro_todoservice/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/todos_service:v0.0.3 .
docker push 192.168.200.23:5002/micro-spring-cloud/todos_service:v0.0.3