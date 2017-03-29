package com.micro.cloud.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maybo on 2016/12/22.
 */
@Service
public class NginxCnfTemplate {

    private Logger logger=Logger.getLogger(this.getClass());

    private static Template template;

    private static InputStream inApp;
    private static InputStream inRun;
    static {
        Configuration cfg = new Configuration();

        inApp= NginxCnfTemplate.class.getClassLoader().getResourceAsStream("ftl/app.ftl");
        inRun=NginxCnfTemplate.class.getClassLoader().getResourceAsStream("run.sh");
        try {

            StringTemplateLoader stringTemplateLoader=new StringTemplateLoader();
            stringTemplateLoader.putTemplate("app",FileUtils.streamToString(inApp));
            cfg.setTemplateLoader(stringTemplateLoader);
            template= cfg.getTemplate("app");
            FileUtils.writeRun(FileUtils.streamToString(inRun));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String,Object> template(Map<String,Object> serviceMap){
        Map<String,Object>resMap=new HashMap<String,Object>();
        if (serviceMap.isEmpty()){
            resMap.put("state","-100");
            resMap.put("message","注册中心没有发现服务");
            return resMap;
        }
        StringWriter writer = new StringWriter();
        Map<String,Object>model=new HashMap<String,Object>();
        model.put("serviceKeys",serviceMap.keySet());
        model.put("serviceMap",serviceMap);
        try {
            template.process(model, writer);
            FileUtils.writeConf(writer.toString());
            try {
               List<String> list= ShellExecute.runShell("sh " + "/usr/local/share/run.sh");

               for (String state:list){
                   if ("-100".equals(state)){
                       resMap.put("state","-200");
                       resMap.put("message","nginx的配置文件不合法，无法启动");
                       break;
                   }
                  else if ("-200".equals(state)){
                       resMap.put("state","-300");
                       resMap.put("message","nginx的配置文件合法，但是nginx还是顽强的启不动");
                       break;
                   }
                   else{
                       resMap.put("state",state);
                       resMap.put("message","成功启动");
                       break;
                   }
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info(writer.toString());
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resMap;
    }
}
