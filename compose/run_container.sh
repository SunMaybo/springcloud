#!/usr/bin/env bash
curl -X PUT -d '{"Datacenter":"dc1","Node": "node1"}' -i http://192.168.200.23:8500/v1/catalog/deregister
docker service create --name=dbConfigCenter -e SERVER_PORT=8888 -e GIT_URI=http://192.168.200.19/mayunbao/dbConfig.git -e GIT_USERNAME=mayunbao -e GIT_PASSWORD=Qwer123456 -p 8888:8888 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.4
docker service create --name=todosApi -e SERVER_PORT=8589 -e CONSUL_PORT=8500 -e CONSUL_DiSCOVERY_IP=192.168.100.95 -p 8589:8589 192.168.200.23:5002/micro-spring-cloud/todos_api:v0.0.2
docker service create --name=todoService -e SERVER_PORT=5467 -e CONFIG_PROFILE=test -e CONFIG_LABEL=master -e CONFIG_URI=http://192.168.100.95:8888 -p 5467:5467 192.168.200.23:5002/micro-spring-cloud/todos_service:v0.0.2
docker service create --name=monitor -e SERVER_PORT=1234 -e CONSUL_PORT=8500 -e CONSUL_DiSCOVERY_IP=192.168.100.95 -p 1234:1234 192.168.200.23:5002/micro-spring-cloud/monitor:v0.0.2
docker service create --name=microTodos -p 6752:6752 192.168.200.23:5002/python/micro_todos python /apps/todos/main.py
docker service create --name=gateway -p 88:80 -e SERVER_PORT=9500 -e CONSUL_HOST=192.168.200.23 -e CONSUL_PORT=8500 192.168.200.23:5002/
micro-spring-cloud/gateway:v0.0.2
docker run -d --name=dockerRegisterConsul -e SWARM_HOST=192.168.100.95 -e SWARM_PORT=2375 -e CONSUL_HOST=192.168.200.23 -e CONSUL_PORT=8500 192.168.200.23:5002/micro-spring-cloud/docker_consul:v0.0.1