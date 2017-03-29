# 在此处输入标题

标签（空格分隔）： 未分类

---

在此输入正文



###
系统环境

      linux:centos7
      docker:1.10.3

###
公用资源
####
 服务器
 > IP:192.168.100.95
 > User:root
 > Password:123abc,.<br/>
 > IP:192.168.100.142
 > User:root
 > Password:123abc,.</br>
 > IP:129.168.200.23
 > User:docker
 >Password:dockeroffice2015


### 数据库资源
```
redis {
	# 密码：juxinli12345
	192.168.200.23:6379	
} 

mongo{
	# 无密码
	192.168.200.23:27017		
}

mysql{
	# 密码: mypass
	# 用户：admin
	192.168.200.23:3306
}

consul{
  192.168.200.23:8500
}

```

 
###
服务器资源分配

       192.168.100.95  用于Springcloud 云平台搭建
       192.168.200.23  用于数据库：mysql，mongodb，redis，consul
       192.168.100.142 用于python服务注册发现

###

JAVA通识
      
####

环境
    
    jdk：1.7
 
## 数据库部署脚本
```bash
# 镜像：https://hub.tenxcloud.com/repos/tenxcloud/redis
docker run -d --name=redis -p 6379:6379 -e REDIS_PASS="juxinli12345" index.tenxcloud.com/tenxcloud/redis

# 镜像：https://hub.tenxcloud.com/repos/tenxcloud/mongodb
docker run -d --name=mongo -p 27017:27017 -p 28017:28017 -e MONGODB_PASS="juxinli12345" index.tenxcloud.com/tenxcloud/mongodb

#镜像: https://hub.tenxcloud.com/repos/tenxcloud/mysql
docker run -d -p 3306:3306 -e MYSQL_PASS="mypass" tutum/mysql

#镜像: https://hub.tenxcloud.com/repos/sdvdxl/consul
docker run -p 8400:8400 -p 8500:8500 -p 53:53/udp -h node1 sequenceiq/consul -server -bootstrap -ui-dir /ui
```
## AppHouse部署

### 环境 
```
# ip: 192.168.200.23
# port: 80
# 用户: admin
# 密码: juxinli2016
```

### 部署脚本

```
docker run --privileged=true -e HOST_IP=192.168.200.23 -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker:/var/lib/docker -v /var/local/apphouse/config:/var/lib/registry_Deploy/install/config -v /var/local/apphouse/storage:/var/lib/registry_Deploy/install/storage index.youruncloud.com/apphouse/apphouse:latest
```

## shipyard部署

### 环境
```
# ip: 192.168.100.95
# port: 8080
# 用户: admin
# 密码: shipyard
``` 

### 部署脚本

  由于镜像下载缓慢可以从时速云下载镜像
  
  地址：https://hub.tenxcloud.com/repos/maybo
  
 ```
 curl -sSL https://shipyard-project.com/deploy | bash -s
 ```
 
### 节点添加脚本
```
curl -sSL https://shipyard-project.com/deploy | ACTION=node DISCOVERY=etcd://192.168.100.95:4001 bash -s
``` 

## dbConfigCenter部署

### 环境
```
ip: 192.168.100.95
port: 8888
```

### 部署脚本

```
docker run -d -p8888:8888 -h=192.168.100.95 --name=dbConfigCenter 192.168.200.23:5002/micro-spring-cloud/dbconfigcenter:v0.0.1 --server.port=8888
```

## dbService部署

### 环境
```
ip: 192.168.100.95
port: 7777
```

### 部署脚本

```
docker run -d -p7777:7777 -h=192.168.100.95 --name=dbService 192.168.200.23:5002/micro-spring-cloud/db_service:v0.0.1 --server.port=7777
