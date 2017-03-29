#!/usr/bin/env bash
rm -rf *.jar
cp -R   micro_dbConfigCenter/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.5 .
docker push 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.5