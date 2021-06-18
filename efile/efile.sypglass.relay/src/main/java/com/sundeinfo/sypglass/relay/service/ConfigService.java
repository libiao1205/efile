package com.sundeinfo.sypglass.relay.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.relay.mapper.relay.ConfigMapper;
import com.sundeinfo.sypglass.relay.model.relay.Config;
import com.sundeinfo.sypglass.relay.model.relay.ConfigExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ConfigService")
public class ConfigService extends AbstractService<ConfigService,Config> {

    @Autowired
    private ConfigMapper configMapper;

    public List<Config> findByNameAndCode(String name,String code){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        return configMapper.selectByExample(example);
    }

    public Config findOneByNameAndCode(String name,String code){
        List<Config> list =  findByNameAndCode(name,code);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    public Config findByNameAndCodeAndSeq(String name,String code,Byte seq){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        criteria.andSeqEqualTo(seq);
        List<Config> list = configMapper.selectByExample(example);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

}