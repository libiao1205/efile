package com.sundeinfo.sypglass.relay.init;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemStarter extends AbstractComponent<SystemStarter> {

    @Autowired
    private SchemaInitializeHandler schemaInitializeHandler;

    @Autowired
    private ScheduleInitializeHandler scheduleInitializeHandler;

    public void initialize() throws Exception {
        logger.info("--------------开始初始化接口服务器数据--------------");
        schemaInitializeHandler.execute();
        scheduleInitializeHandler.execute();
        logger.info("--------------接口服务器已经初始化完成--------------");
    }
}
