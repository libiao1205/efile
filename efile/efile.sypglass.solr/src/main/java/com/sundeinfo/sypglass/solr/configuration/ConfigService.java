package com.sundeinfo.sypglass.solr.configuration;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.solr.mapper.ConfigMapper;
import com.sundeinfo.sypglass.solr.model.Config;
import com.sundeinfo.sypglass.solr.model.ConfigExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ConfigService")
public class ConfigService extends AbstractService<ConfigService,Config> {

    @Autowired
    private ConfigMapper configMapper;

    public Config create(String name,String code,String value,String commnet){
        Config config = new Config();
        config.setName(name);
        config.setCode(code);
        config.setSeq((byte)1);
        config.setValue(value);
        config.setStatus((byte)1);
        config.setComment(commnet);
        return config;
    }

    public Config create(String name,String code,int seq,String value,String commnet){
        Config config = create(name,code,value,commnet);
        config.setSeq((byte)seq);
        return config;
    }

    public List<Config> findByName(String name){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        return configMapper.selectByExample(example);
    }

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

    public int insert(Config config){
        return configMapper.insert(config);
    }

    @Transactional
    public int editConfig(long id,String value){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        Config config = configMapper.selectByPrimaryKey(id);
        config.setValue(value);
       return configMapper.updateByPrimaryKeySelective(config);
    }


}