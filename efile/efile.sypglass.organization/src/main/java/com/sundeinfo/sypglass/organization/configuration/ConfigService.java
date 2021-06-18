package com.sundeinfo.sypglass.organization.configuration;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.organization.define.SypGlassConfig;
import com.sundeinfo.sypglass.organization.dto.PasswordComplexityDto;
import com.sundeinfo.sypglass.organization.dto.PasswordExceedDto;
import com.sundeinfo.sypglass.organization.dto.PasswordLockDto;
import com.sundeinfo.sypglass.organization.mapper.ConfigMapper;
import com.sundeinfo.sypglass.organization.model.Config;
import com.sundeinfo.sypglass.organization.model.ConfigExample;
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
    public int editConfig(Config config){
       return configMapper.updateByPrimaryKeySelective(config);
    }

    public PasswordExceedDto psdExceedFindByName(){
        PasswordExceedDto passwordExceedDto = new PasswordExceedDto();
        List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_EXCEED);
        configList.forEach((dto) ->{
            if(SypGlassConfig.PASSWORD_USABLE_DAY.equals(dto.getCode())){
                passwordExceedDto.setUsableDay(dto.getValue());
                passwordExceedDto.setState(dto.getStatus().toString());
            }else if(SypGlassConfig.PASSWORD_AHEAD_DAY.equals(dto.getCode())){
                passwordExceedDto.setAheadDay(dto.getValue());
            }
        });
        return passwordExceedDto;
    }

    public PasswordComplexityDto psdComplexityFindByName(){
        PasswordComplexityDto passwordComplexityDto = new PasswordComplexityDto();
        List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_COMPLEXITY);
        configList.forEach((dto) ->{
            if(SypGlassConfig.PSD_CHECK.equals(dto.getCode())){
                passwordComplexityDto.setPsdCheck(dto.getValue());
            }else if(SypGlassConfig.PSD_LENGTH.equals(dto.getCode())){
                passwordComplexityDto.setPsdLength(dto.getValue());
            }
        });
        return passwordComplexityDto;
    }
    public String checkPsdComplexity(String password){
        PasswordComplexityDto passwordComplexityDto = this.psdComplexityFindByName();
        if(passwordComplexityDto == null){
            return "";
        }
        if(password.length() < Integer.valueOf(passwordComplexityDto.getPsdLength())){
            return String.format("密码长度不足%s位",passwordComplexityDto.getPsdLength());
        }
        if("1".equals(passwordComplexityDto.getPsdCheck())){
            if (password.matches(SypGlassConfig.PSD_CHECK_TOP1)){
                return "";
            }else{
                return "密码必须是大写字母、小写字母与数字的组合";
            }
        }
        if("2".equals(passwordComplexityDto.getPsdCheck())){
            if (password.matches(SypGlassConfig.PSD_CHECK_TOP2)){
                return "";
            }else{
                return "密码必须是字母、数字、特殊字符的组合";
            }
        }
        return "";
    }

    @Transactional
    public int updatePasswordExceed(PasswordExceedDto passwordExceedDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_EXCEED);
            if(configList.size() == 0){
                this.insert(this.create(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_USABLE_DAY,1,passwordExceedDto.getUsableDay(),"强制密码修改天数"));
                this.insert(this.create(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_AHEAD_DAY,2,passwordExceedDto.getAheadDay(),"提前几天提醒修改密码"));
                return 1;
            }
            Config record = new Config();
            record.setValue(passwordExceedDto.getUsableDay());
            record.setStatus(Byte.valueOf(passwordExceedDto.getState()));
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_USABLE_DAY,record);
            record.setValue(passwordExceedDto.getAheadDay());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_AHEAD_DAY,record);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public int updatePasswordMistakeCount(PasswordLockDto passwordLockDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PSD_LOCK);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT,1,passwordLockDto.getPsdMistakeCount(),"锁定密码错误次数"));
            }
            Config record = new Config();
            record.setValue(passwordLockDto.getPsdMistakeCount());
            record.setStatus(Byte.valueOf(passwordLockDto.getState()));
            return this.updateByExampleSelective(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public int updatePasswordComplexity(PasswordComplexityDto passwordComplexityDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_COMPLEXITY);
            if(configList.size() == 0){
                this.insert(this.create(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_CHECK,1,passwordComplexityDto.getPsdCheck(),"校验级别"));
                this.insert(this.create(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_LENGTH,2,passwordComplexityDto.getPsdLength(),"长度"));
                return 1;
            }
            Config record = new Config();
            record.setValue(passwordComplexityDto.getPsdCheck());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_CHECK,record);
            record.setValue(passwordComplexityDto.getPsdLength());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_LENGTH,record);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private int updateByExampleSelective(String name,String code,Config record){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        return configMapper.updateByExampleSelective(record,example);
    }

}