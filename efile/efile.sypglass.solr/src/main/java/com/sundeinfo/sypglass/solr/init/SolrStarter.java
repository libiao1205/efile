package com.sundeinfo.sypglass.solr.init;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SolrStarter extends AbstractComponent<SolrStarter> implements ApplicationRunner {

    @Autowired
    private SolrScheduleInitializeHandler solrScheduleInitializeHandler;

    public void initialize() throws Exception {
        logger.info("--------------开始初始化定时Solr更新服务配置--------------");
        solrScheduleInitializeHandler.execute();
        logger.info("--------------定时Solr更新服务配置初始化完成--------------");
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.initialize();
    }
}
