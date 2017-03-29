#!/usr/bin/env bash
cd ~/micro_springcloud/
rm -rf *.jar
#mvn clean
#mvn package
cp -R micro_todoservice/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/todos_service:v0.0.2 .
docker push 192.168.200.23:5002/micro-spring-cloud/todos_service:v0.0.2

rm -rf *.jar
cp -R  micro_todosclient/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/todos_api:v0.0.2 .
docker push 192.168.200.23:5002/micro-spring-cloud/todos_api:v0.0.2

rm -rf *.jar
cp -R   micro_monitor/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/monitor:v0.0.2 .
docker push 192.168.200.23:5002/micro-spring-cloud/monitor:v0.0.2

rm -rf *.jar
cp -R   micro_dbConfigCenter/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.2 .
docker push 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.2

rm -rf *.jar
cp -R   micro_template/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/gateway:v0.0.2 .
docker push 192.168.200.23:5002/micro-spring-cloud/gateway:v0.0.2

rm -rf *.jar
cp -R   micro_docker/target/*.jar .
docker build -t 192.168.200.23:5002/micro-spring-cloud/docker_consul:v0.0.1 .
docker push 192.168.200.23:5002/micro-spring-cloud/docker_consul:v0.0.1