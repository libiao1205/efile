package com.sundeinfo.core.actuate;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("ServerHealthIndicator")
public class ServerHealthIndicator extends AbstractComponent<ServerHealthIndicator>{

    @Autowired
    ApplicationContext context;


    public HealthMap executor() {

        HealthMap healthMap = new HealthMap();
        HealthIndicator executor = null;

        Map<String, HealthIndicator> executorMap = context.getBeansOfType(HealthIndicator.class);

        //磁盘健康检查 key:diskSpaceHealthIndicator
        executor = executorMap.get("diskSpaceHealthIndicator");
        Health deskHealth = executor.health();
        healthMap.put("desk",deskHealth);

        //mongo健康检查
        executor = executorMap.get("mongoHealthIndicator");
        Health mongoHealth = executor.health();
        healthMap.put("mongo",mongoHealth);

        //数据库健康检查
        executor = executorMap.get("dbHealthIndicator");
        Health dbHealth = executor.health();
        healthMap.put("db",dbHealth);

        //自定义健康检查
        healthMap.put("user",userHealth());

        return healthMap;
    }

    private Health userHealth(){
        return new Health.Builder().withDetail("usercount", 10) //自定义监控内容
                .withDetail("userstatus", "up").up().build();
    }

    private void createEndpoint(){
        Map<String, Endpoint> r = context.getBeansOfType(Endpoint.class);
        Endpoint<Map<String, Object>> endpoint = r.get("environmentEndpoint");
        Map<String, Object>  map = endpoint.invoke();
    }

}
