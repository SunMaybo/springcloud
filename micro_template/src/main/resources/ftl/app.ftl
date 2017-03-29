<#list serviceMap?keys as k>
upstream ${k}{
<#assign item = serviceMap[k]>
<#list item as service>
server ${service.host}:${service.port?c};
</#list>
}
</#list>
server {
listen       80;
server_name  localhost;
<#list serviceMap?keys as k>
location /${k}/ {
proxy_pass http://${k}/;
}
</#list>

}