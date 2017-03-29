package com.micro.cloud.docker.registry;

import java.util.Map;

/**
 * Created by maybo on 2016/12/26.
 */
public interface RegisterService {

    public void register(Service service);

    public void deRegister(String serviceId);

    public Map<String,Object> services();

}
