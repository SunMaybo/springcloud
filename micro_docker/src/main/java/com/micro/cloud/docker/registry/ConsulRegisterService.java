package com.micro.cloud.docker.registry;

import com.ecwid.consul.ConsulException;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.catalog.model.CatalogRegistration;
import com.ecwid.consul.v1.health.model.HealthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/26.
 */
public class ConsulRegisterService implements RegisterService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ConsulClient consulClient;

    @Override
    public void register(Service service) {
        CatalogRegistration catalogRegistration = new CatalogRegistration();
        NewService newService = new NewService();
        newService.setAddress(service.getHost());
        newService.setId(service.getServiceId());
        newService.setName(service.getServiceName());
        newService.setPort(service.getPort());
        newService.setTags(service.getTags());
        NewService.Check check = new NewService.Check();
        newService.setCheck(createCheck(service.getPort(), service.getHost()));
        try {
            consulClient.agentServiceRegister(newService);
            logger.info("register:" + newService.toString());
        } catch (ConsulException e) {
            logger.error(e.getMessage());
        }
    }

    private NewService.Check createCheck(Integer port, String host) {
        NewService.Check check = new NewService.Check();
        check.setHttp(String.format("%s://%s:%s%s", new Object[]{"http", host, port, "/health"}));
        check.setInterval("10s");
        return check;
    }

    @Override
    public void deRegister(String serviceId) {
        try {
            consulClient.agentServiceDeregister(serviceId);
        } catch (ConsulException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Map<String,Object> services() {
        Response<Map<String, List<String>>> catalogServices=consulClient.getCatalogServices(null, null);
        Map<String,List<String>> serviceMap = catalogServices.getValue();
        Map<String,Object> serviceMapList= new HashMap<String,Object>();
        Iterator iterator = serviceMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Response<List<HealthService>> healthServiceResponse = consulClient.getHealthServices(key, false, null);
            List<HealthService> healthServiceList=healthServiceResponse.getValue();
            if (null!=healthServiceList&&!healthServiceList.isEmpty()) {
                for (HealthService healthService:healthServiceList) {
                    if (healthService.getService().getService().contains("consul")){
                        continue;
                    }
                    Service serviceInfo = new Service();
                    serviceInfo.setHost(healthService.getService().getAddress());
                    serviceInfo.setPort(healthService.getService().getPort());
                    serviceInfo.setServiceId(healthService.getService().getId());
                    serviceInfo.setServiceName(healthService.getService().getService());
                    serviceInfo.setTags(healthService.getService().getTags());
                    serviceMapList.put(healthService.getService().getId(), serviceInfo);
                }
            }
        }
        return serviceMapList;
    }
}

