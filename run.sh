#!/usr/bin/env bash
command="java -Djava.security.egd=file:/dev/./urandom -jar $1 /app/app.jar"

if [ -n "$SERVER_PORT" ]; then
    command=${command}" --server.port=${SERVER_PORT}"
fi

if [ -n "$SERVICE_NAME" ]; then
    command=${command}" --spring.application.name=${SERVICE_NAME}"
fi

if [ -n "$CONFIG_PROFILE" ]; then
    command=${command}" --spring.cloud.config.profile=${CONFIG_PROFILE}"
fi

if [ -n "$CONFIG_LABEL" ]; then
    command=${command}" --spring.cloud.config.label=${CONFIG_LABEL}"
fi

if [ -n "$CONFIG_URI" ]; then
    command=${command}" --spring.cloud.config.uri=${CONFIG_URI}"
fi

if [ -n "$CONSUL_HOST" ]; then
    command=${command}" --spring.cloud.consul.host=${CONSUL_HOST}"
fi

if [ -n "$CONSUL_PORT" ]; then
    command=${command}" --spring.cloud.consul.port=${CONSUL_PORT}"
fi

if [ -n "$CONSUL_DiSCOVERY_IP" ]; then
    command=${command}" --spring.cloud.consul.discovery.preferIpAddress=true --spring.cloud.consul.discovery.ipAddress=${CONSUL_DiSCOVERY_IP}"
fi

if [ -n "$CONSUL_DiSCOVERY_HEALTHCHECKINTERVAL" ]; then
    command=${command}" --spring.cloud.consul.discovery.healthCheckInterval=${CONSUL_DiSCOVERY_HEALTHCHECKINTERVAL}"
fi

if [ -n "$GIT_URI" ]; then
    command=${command}" --spring.cloud.config.server.git.uri=${GIT_URI}"
fi

if [ -n "$GIT_USERNAME" ]; then
    command=${command}" --spring.cloud.config.server.git.username=${GIT_USERNAME}"
fi

if [ -n "$GIT_PASSWORD" ]; then
    command=${command}" --spring.cloud.config.server.git.password=${GIT_PASSWORD}"
fi

if [ -n "$GIT_SEARCHPATHS" ]; then
    command=${command}" --spring.cloud.config.server.git.searchPaths=${GIT_SEARCHPATHS}"
fi
if [ -n "$TURBINE_CLUSTERS" ]; then
    command=${command}" --spring.boot.admin.turbine.clusters=${TURBINE_CLUSTERS}"
fi
if [ -n "$TURBINE_URL" ]; then
    command=${command}" --spring.boot.admin.turbine.url=${TURBINE_URL}"

if [ -n "$TURBINE_CLUSTERNAME" ]; then
    command=${command}" --turbine.clusterNameExpression=new String(\"${TURBINE_CLUSTERNAME}\")"
fi
if [ -n "$TURBINE_APPCONFIG" ]; then
    command=${command}" --turbine.appConfig=${TURBINE_APPCONFIG}"
fi
if [ -n "$CONSUL_DISCOVERY_INTERNAL" ]; then
    command=${command}" --spring.cloud.consul.discovery.internal=${CONSUL_DISCOVERY_INTERNAL}"
fi
if [ -n "$CONSUL_DISCOVERY_REFRESH" ]; then
    command=${command}" --spring.cloud.consul.discovery.refresh=${CONSUL_DISCOVERY_REFRESH}"
fi
if [ -n "$SWARM_HOST" ]; then
    command=${command}" --docker.swarm.host=${SWARM_HOST}"
fi
if [ -n "$SWARM_PORT" ]; then
    command=${command}" --docker.swarm.port=${SWARM_PORT}"
fi
echo ${command}
exec ${command}