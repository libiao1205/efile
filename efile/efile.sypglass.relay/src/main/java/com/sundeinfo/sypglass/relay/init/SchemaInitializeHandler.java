package com.sundeinfo.sypglass.relay.init;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.relay.define.RelayDefine;
import com.sundeinfo.sypglass.relay.mapper.relay.OASchemaMapper;
import com.sundeinfo.sypglass.relay.mapper.oa.SchemaMapper;
import com.sundeinfo.sypglass.relay.model.relay.Config;
import com.sundeinfo.sypglass.relay.model.relay.OASchema;
import com.sundeinfo.sypglass.relay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RelaySchemaService")
public class SchemaInitializeHandler extends AbstractService<SchemaInitializeHandler,OASchema> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private SchemaMapper schemaMapper;

    @Autowired
    private OASchemaMapper oaSchemaMapper;

    public void execute(){
        logger.debug("初始化OA数据库Schema信息到本地库");
        String[] schemaSetting = RelayDefine.CONFIG_SCHEMA_INITIALIZE.split(",");
        Config config = configService.findByNameAndCodeAndSeq(schemaSetting[0],schemaSetting[1],Byte.parseByte(schemaSetting[2]));
        if (config != null && "1".equals(config.getValue())){
            logger.info("--------------Schema信息初始化完成--------------");
            return;
        }
        List<OASchema> oaSchemaList = schemaMapper.selectAll();
        logger.debug("--------------读取OA数据库表结构完成--------------");
        for (OASchema schema:oaSchemaList) {
            oaSchemaMapper.insert(schema);
        }
        logger.debug("--------------Schema信息初始化完成--------------");
    }
}