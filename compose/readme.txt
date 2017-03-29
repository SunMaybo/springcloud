docker service create
    --name=dbConfigCenter
    -e SERVER_PORT=8888
    -e GIT_URI=http://192.168.200.19/mayunbao/dbConfig.git
    -e GIT_USERNAME=mayunbao
    -e GIT_PASSWORD=Qwer123456
    -p 8888:8888 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.2